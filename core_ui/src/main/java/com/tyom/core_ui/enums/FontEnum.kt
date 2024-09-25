package com.tyom.core_ui.enums

import androidx.annotation.FontRes
import com.tyom.core_ui.R

enum class FontEnum(
    @FontRes val fontRes: Int,
    val fontName: String
) {
    GOWUN_BATANG_BOLD(R.font.gowun_batang_bold, "Gowun batang bold"),
    GOWUN_BATANG_REGULAR(R.font.gowun_batang_regular, "Gowun batang regular"),

    HANDJET_LIGHT(R.font.handjet_light, "Handjet light"),
    HANDJET_MEDIUM(R.font.handjet_medium, "Handjet medium"),
    HANDJET_REGULAR(R.font.handjet_regular, "Handjet regular"),

    NUNITO_SANS_CONDENSED_EXTRA_BOLD(R.font.nunito_sans_condensed_extra_bold, "Nunito sans condensed extra bold"),
    NUNITO_SANS_CONDENSED_EXTRA_BOLD_ITALIC(R.font.nunito_sans_condensed_extra_bold_italic, "Nunito sans condensed extra bold italic"),
    NUNITO_SANS_CONDENSED_EXTRA_LIGHT(R.font.nunito_sans_condensed_extra_light, "Nunito sans condensed extra light"),
    NUNITO_SANS_CONDENSED_EXTRA_LIGHT_ITALIC(R.font.nunito_sans_condensed_extra_light_italic, "Nunito sans condensed extra light italic"),
    NUNITO_SANS_CONDENSED_MEDIUM(R.font.nunito_sans_condensed_medium, "Nunito sans condensed medium"),
    NUNITO_SANS_CONDENSED_MEDIUM_ITALIC(R.font.nunito_sanst_condensed_medium_italic, "Nunito sans condensed medium italic"),

    PLAYFAIR_DISPLAY_BOLD(R.font.playfair_display_bold, "Playfair display bold"),
    PLAYFAIR_DISPLAY_BOLD_ITALIC(R.font.playfair_display_bold_italic, "Playfair display bold italic"),
    PLAYFAIR_DISPLAY_ITALIC(R.font.playfair_display_italic, "Playfair display italic"),
    PLAYFAIR_DISPLAY_REGULAR(R.font.playfair_display_regular, "Playfair display regular"),

    PLAYWRITE_DEGRUND_REGULAR(R.font.playwrite_degrund_regular, "Playwrite degrund regular"),
    PLAYWRITE_DEGRUND_THIN(R.font.playwrite_degrund_thin, "Playwrite degrund thin"),

    RALEWAY_BLACK(R.font.raleway_black, "Raleway black"),
    RALEWAY_BLACK_ITALIC(R.font.raleway_black_italic, "Raleway black italic"),
    RALEWAY_BLACK_EXTRA_LIGHT(R.font.raleway_extra_light, "Raleway extra light"),
    RALEWAY_BLACK_EXTRA_LIGHT_ITALIC(R.font.raleway_extra_light_italic, "Raleway extra light italic"),
    RALEWAY_BLACK_MEDIUM(R.font.raleway_medium, "Raleway medium"),
    RALEWAY_BLACK_MEDIUM_ITALIC(R.font.raleway_medium, "Raleway medium italic"),
    RALEWAY_BLACK_THIN(R.font.raleway_thin, "Raleway thin"),
    RALEWAY_BLACK_THIN_ITALIC(R.font.raleway_thin_italic, "Raleway thin italic"),

    SATISFY_REGULAR(R.font.satisfy_regular, "satisfy_regular")

}