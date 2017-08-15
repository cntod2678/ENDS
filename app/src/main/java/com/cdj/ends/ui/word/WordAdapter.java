package com.cdj.ends.ui.word;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cdj.ends.R;
import com.cdj.ends.base.util.ChromeTabActionBuilder;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.data.Word;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import com.cdj.ends.ui.word.viewmodel.Section;

import static com.cdj.ends.Config.TRANS_NAVER_BASE_URL;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

public class WordAdapter extends SectioningAdapter {
    private static final String TAG = "WordAdapter";

    private Context mContext;
    private Realm mRealm;

    class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView txtItemWord;
        TextView txtItemWordTranslated;
        Button btnMore;

        ItemViewHolder(View itemView, boolean showAdapterPosition) {
            super(itemView);
            txtItemWord = (TextView) itemView.findViewById(R.id.txtItem_word);
            txtItemWordTranslated = (TextView) itemView.findViewById(R.id.txtItem_translated);
            btnMore = (Button) itemView.findViewById(R.id.btnMore);
        }
    }

    class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder implements View.OnClickListener {
        TextView txtHeaderDate;
        ImageButton collapseButton;

        HeaderViewHolder(View itemView, boolean showAdapterPosition) {
            super(itemView);
            txtHeaderDate = (TextView) itemView.findViewById(R.id.txtHeader_Date);
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

    public class FooterViewHolder extends SectioningAdapter.FooterViewHolder {
        TextView textView;

        public FooterViewHolder(View itemView, boolean showAdapterPosition) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    private ArrayList<Section> sections = new ArrayList<>();

    private boolean showAdapterPositions;

    public WordAdapter(Context context, Realm realm, boolean showAdapterPositions) {
        this.showAdapterPositions = showAdapterPositions;
        mContext = context;
        mRealm = realm;

        RealmResults<Word> dateList = mRealm.where(Word.class).distinct("date");
        RealmResults<Word> wordsAllList = mRealm.where(Word.class).findAllSorted("date", Sort.ASCENDING);

        for(Word date : dateList) {
            int sectionIdx = 0;
            List<Word> temp = new ArrayList<>();

            for(Word word : wordsAllList) {
                if(word.getDate().equals(date.getDate())) {
                    temp.add(word);
                }
            }
            appendSection(sectionIdx, temp);
        }

    }

    private void appendSection(int index, List<Word> words) {
        Section section = new Section();
        section.index = index;
        section.header = words.get(0).getDate();
        section.footer = "단어 개수 : " + words.size();

        for (Word word : words) {
            section.wordsItems.add(new Word(word.getWord(), word.getTranslatedWord(), word.getDate(), word.isChkEdu()));
        }
        sections.add(section);
    }


    public void deleteSelection() {
        traverseSelection(new SectioningAdapter.SelectionVisitor() {
            @Override
            public void onVisitSelectedSection(int sectionIndex) {
                Log.d(TAG, "onVisitSelectedSection() called with: " + "sectionIndex = [" + sectionIndex + "]");
                // todo 날짜 모두 삭제
                // deleteAllFromRealm
//                sections.remove(sectionIndex);
//                notifySectionRemoved(sectionIndex);
            }

            @Override
            public void onVisitSelectedSectionItem(int sectionIndex, int itemIndex) {
                Log.d(TAG, "onVisitSelectedSectionItem() called with: " + "sectionIndex = [" + sectionIndex + "], itemIndex = [" + itemIndex + "]");
                Section section = sections.get(sectionIndex);
                if (section != null) {
                    RealmResults<Word> deleteItemWord = mRealm.where(Word.class).equalTo("word", section.wordsItems.get(itemIndex).getWord()).findAll();

                    if(deleteItemWord.size() > 0) {
                        mRealm.beginTransaction();
                        deleteItemWord.deleteFromRealm(0);
                        mRealm.commitTransaction();
                    }

//                    mRealm.close();

                    section.wordsItems.remove(itemIndex);
                    notifySectionItemRemoved(sectionIndex, itemIndex);
                }
            }

            @Override
            public void onVisitSelectedFooter(int sectionIndex) {

            }
        });

        // clear selection without notification - because that would fight the deletion animations triggered above
        clearSelection(false);
    }

    private void onToggleSectionCollapse(int sectionIndex) {
        Log.d(TAG, "onToggleSectionCollapse() called with: " + "sectionIndex = [" + sectionIndex + "]");
        setSectionIsCollapsed(sectionIndex, !isSectionCollapsed(sectionIndex));
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).wordsItems.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return !TextUtils.isEmpty(sections.get(sectionIndex).header);
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return !TextUtils.isEmpty(sections.get(sectionIndex).footer);
    }


    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_selectable_item, parent, false);
        return new ItemViewHolder(v, showAdapterPositions);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_selectable_header, parent, false);
        return new HeaderViewHolder(v, showAdapterPositions);
    }

    @Override
    public FooterViewHolder onCreateFooterViewHolder(ViewGroup parent, int footerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_selectable_footer, parent, false);
        return new FooterViewHolder(v, showAdapterPositions);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(final SectioningAdapter.ItemViewHolder viewHolder, final int sectionIndex, final int itemIndex, int itemType) {
        final Section s = sections.get(sectionIndex);
        ItemViewHolder ivh = (ItemViewHolder) viewHolder;
        ivh.txtItemWord.setText(s.wordsItems.get(itemIndex).getWord());
        ivh.txtItemWordTranslated.setText(s.wordsItems.get(itemIndex).getTranslatedWord());

        ivh.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChromTab(s.wordsItems.get(itemIndex).getWord());
            }
        });

        ivh.itemView.setActivated(isSectionItemSelected(sectionIndex, itemIndex));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;

        hvh.txtHeaderDate.setText(s.header);

        hvh.itemView.setActivated(isSectionSelected(sectionIndex));
        hvh.updateSectionCollapseToggle(isSectionCollapsed(sectionIndex));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindFooterViewHolder(SectioningAdapter.FooterViewHolder viewHolder, int sectionIndex, int footerType) {
        Section s = sections.get(sectionIndex);
        FooterViewHolder fvh = (FooterViewHolder) viewHolder;
        fvh.textView.setText(s.footer);
        fvh.itemView.setActivated(isSectionFooterSelected(sectionIndex));
    }

    private void openChromTab(String queryString) {
        String url = TRANS_NAVER_BASE_URL + "&query=" + queryString;
        ChromeTabActionBuilder.openChromTab(mContext, url);
    }
}
