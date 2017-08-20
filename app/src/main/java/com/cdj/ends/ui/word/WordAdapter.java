package com.cdj.ends.ui.word;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cdj.ends.R;
import com.cdj.ends.data.Word;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 18..
 */

public class WordAdapter extends SectioningAdapter {

    private static final String TAG = "WordAdapter";

    private DeleteWordListener deleteWordListener;

    private boolean showAdapterPositions;

    private Context mContext;

    private List<Section> mSectionList;

    interface DeleteWordListener {
        void deleteWord(List<Word> wordList);
    }

    public WordAdapter(Context context, List<Section> sections, boolean showAdapterPositions) {
        this.mContext = context;
        mSectionList = new ArrayList<>();
        this.mSectionList = sections;
        this.showAdapterPositions = showAdapterPositions;
        deleteWordListener = (DeleteWordListener) mContext;
    }

    class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder implements View.OnClickListener {
        TextView txtSectionHeader;
        ImageButton collapseButton;

        public HeaderViewHolder(View itemView, boolean showAdapterPosition) {
            super(itemView);
            txtSectionHeader = (TextView) itemView.findViewById(R.id.txtSection_header);
            collapseButton = (ImageButton) itemView.findViewById(R.id.collapseButton);
            collapseButton.setOnClickListener(this);
        }

        void updateSectionCollapseToggle(boolean sectionIsCollapsed) {
            @DrawableRes int id = sectionIsCollapsed
                    ? R.drawable.ic_expand_more_black_24dp
                    : R.drawable.ic_expand_less_black_24dp;

            collapseButton.setImageDrawable(ContextCompat.getDrawable(collapseButton.getContext(), id));
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            final int section = WordAdapter.this.getSectionForAdapterPosition(position);
            WordAdapter.this.onToggleSectionCollapse(section);
            updateSectionCollapseToggle(WordAdapter.this.isSectionCollapsed(section));
        }
    }

    class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView txtOriginWord;
        TextView txtTranslatedWord;
        TextView txtAdapterPositionItem;

        public ItemViewHolder(View itemView, boolean showAdapterPosition) {
            super(itemView);

            txtOriginWord = (TextView) itemView.findViewById(R.id.txtOrigin_word);
            txtTranslatedWord = (TextView) itemView.findViewById(R.id.txtTranslated_word);
            txtAdapterPositionItem = (TextView) itemView.findViewById(R.id.txtAdapter_position_item) ;

            if(showAdapterPosition) {
                txtAdapterPositionItem.setVisibility(View.INVISIBLE);
            }
        }
    }

    class FooterViewHolder extends SectioningAdapter.FooterViewHolder {
        TextView txtWordNums;

        public FooterViewHolder(View itemView, boolean showAdapterPosition) {
            super(itemView);
            txtWordNums = (TextView) itemView.findViewById(R.id.txtWord_nums);
        }
    }

    public void deleteSelection() {
        traverseSelection(new SelectionVisitor() {
            @Override
            public void onVisitSelectedSection(int sectionIndex) {
                Log.d(TAG, "onVisitSelectedSection() called with: " + "sectionIndex = [" + sectionIndex + "]");
                mSectionList.remove(sectionIndex);
                notifySectionRemoved(sectionIndex);
            }

            @Override
            public void onVisitSelectedSectionItem(int sectionIndex, int itemIndex) {
                Log.d(TAG, "onVisitSelectedSectionItem() called with: " + "sectionIndex = [" + sectionIndex + "], itemIndex = [" + itemIndex + "]");
                Section section = mSectionList.get(sectionIndex);
                if (section != null) {
                    //todo
                    List<Word> deleteWordList = new ArrayList<Word>();
                    deleteWordList.add(section.items.get(itemIndex));
                    Log.d(TAG, "선택된 삭제 " +  deleteWordList.size());
                    deleteWordListener.deleteWord(deleteWordList);

                    section.items.remove(itemIndex);
                    notifySectionItemRemoved(sectionIndex, itemIndex);
                }
            }

            @Override
            public void onVisitSelectedFooter(int sectionIndex) {
                Section section = mSectionList.get(sectionIndex);
                if (section != null) {
                    section.footer = null;
                    notifySectionFooterRemoved(sectionIndex);
                }
            }
        });

        clearSelection(false);
    }

    private void onToggleSectionCollapse(int sectionIndex) {

        setSectionIsCollapsed(sectionIndex, !isSectionCollapsed(sectionIndex));
    }

    @Override
    public int getNumberOfSections() {
        return mSectionList.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mSectionList.get(sectionIndex).items.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return !TextUtils.isEmpty(mSectionList.get(sectionIndex).header);
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return !TextUtils.isEmpty(mSectionList.get(sectionIndex).footer);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_word_header, parent, false);
        return new HeaderViewHolder(view, showAdapterPositions);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_word_item, parent, false);
        return new ItemViewHolder(view, showAdapterPositions);
    }

    @Override
    public FooterViewHolder onCreateFooterViewHolder(ViewGroup parent, int footerType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_word_footer, parent, false);
        return new FooterViewHolder(view, showAdapterPositions);
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = mSectionList.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;

        hvh.txtSectionHeader.setText(s.header);

        hvh.itemView.setActivated(isSectionSelected(sectionIndex));
        hvh.updateSectionCollapseToggle(isSectionCollapsed(sectionIndex));
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section s = mSectionList.get(sectionIndex);
        ItemViewHolder ivh = (ItemViewHolder) viewHolder;
        ivh.txtOriginWord.setText(s.items.get(itemIndex).getWord());
        ivh.txtTranslatedWord.setText(s.items.get(itemIndex).getTranslatedWord());
        ivh.txtAdapterPositionItem.setText(Integer.toString(getAdapterPositionForSectionItem(sectionIndex, itemIndex)));

        ivh.itemView.setActivated(isSectionItemSelected(sectionIndex, itemIndex));
    }

    @Override
    public void onBindFooterViewHolder(SectioningAdapter.FooterViewHolder viewHolder, int sectionIndex, int footerType) {
        Section s = mSectionList.get(sectionIndex);
        FooterViewHolder fvh = (FooterViewHolder) viewHolder;
        fvh.txtWordNums.setText("외워야 할 단어 : " + s.footer);

        fvh.itemView.setActivated(isSectionFooterSelected(sectionIndex));
    }


}
