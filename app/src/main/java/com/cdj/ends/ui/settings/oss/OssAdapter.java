package com.cdj.ends.ui.settings.oss;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdj.ends.R;
import com.cdj.ends.base.util.ChromeTabActionBuilder;
import com.cdj.ends.data.OpenSource;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 20..
 */



public class OssAdapter extends RecyclerView.Adapter<OssAdapter.OpenSourceItemHolder> {

    private static final String TAG = "OssAdapter";

    private List<OpenSource> mOpenSources;

    public OssAdapter() {}

    public OssAdapter(List<OpenSource> openSources) {
        mOpenSources = openSources;
        Log.d(TAG, openSources.size() + " ");
    }

    @Override
    public OpenSourceItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_open_source, parent, false);
        OpenSourceItemHolder openSourceItemHolder = new OpenSourceItemHolder(view);
        return openSourceItemHolder;
    }

    @Override
    public void onBindViewHolder(OpenSourceItemHolder holder, int position) {
        ((OpenSourceItemHolder) holder).bind(mOpenSources.get(position));
    }

    @Override
    public int getItemCount() {
        return mOpenSources.size();
    }


    static class OpenSourceItemHolder extends RecyclerView.ViewHolder {
        TextView txtOssName;
        TextView txtOssUrl;
        TextView txtOssCopyright;
        TextView txtOssLicense;

        public OpenSourceItemHolder(View itemView) {
            super(itemView);
            txtOssName = (TextView) itemView.findViewById(R.id.txtOss_name);
            txtOssUrl = (TextView) itemView.findViewById(R.id.txtOss_url);
            txtOssCopyright = (TextView) itemView.findViewById(R.id.txtOss_copyright);
            txtOssLicense = (TextView) itemView.findViewById(R.id.txtOss_license);
        }

        public void bind(final OpenSource oss) {
            txtOssName.setText(oss.getOss());


            txtOssUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChromeTabActionBuilder.openChromTab(itemView.getContext(), oss.getUrl());
                }
            });

            SpannableString content = new SpannableString(oss.getUrl());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            txtOssUrl.setText(content);

            String copyright = "";
           for(int i = 0; i < oss.getCopyrights().size(); i++) {
               copyright += oss.getCopyrights().get(i);
               if(i+1 == oss.getCopyrights().size()) {
                   break;
               }
               copyright += "\n";
           }
            txtOssCopyright.setText(copyright);

            String license = "";
            for(int i = 0; i < oss.getLicenses().size(); i++) {
                license += oss.getLicenses().get(i);
                if(i + 1 == oss.getLicenses().size()) {
                    break;
                }
                license += "\n";
            }
            txtOssLicense.setText(license);
        }
    }
}
