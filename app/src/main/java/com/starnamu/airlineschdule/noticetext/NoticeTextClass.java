package com.starnamu.airlineschdule.noticetext;

import java.util.Random;

/**
 * Created by star_namu on 2015-10-05.
 */
public class NoticeTextClass {
    String NoticeText[] = {"생활화된 안전의식\n\n사라지는 산업재해",
            "안전수칙 지킨만큼\n\n안전사고 예방된다",
            "지킨만큼 안전오고\n\n어긴만큼 위험온다",
            "작은실수 눈감으면\n\n평생두고 후회한다",
            "직장안전 동료사랑\n\n가정안전 가족사랑",
            "할수있다 안전조업\n\n자신있다 IRR방지",
            "부주의한 순간방심\n\n돌아오는 평생후회"
    };

    private int getRandomInt() {
        Random generator = new Random();
        int RandomInt = generator.nextInt(6);
        return RandomInt;
    }

    public String getText() {

        return NoticeText[getRandomInt()];
    }
}
