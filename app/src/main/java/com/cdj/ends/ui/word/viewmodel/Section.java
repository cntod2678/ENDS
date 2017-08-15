package com.cdj.ends.ui.word.viewmodel;

import com.cdj.ends.data.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

public class Section {
    public int index;
    public String header;
    public String footer;
    public List<Word> wordsItems = new ArrayList<>();
}
