package com.tyom.core_ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.tyom.core_ui.R

@Composable
fun gowunBatangRegularTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.gowun_batang_regular)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun gowunBatangBoldTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.gowun_batang_bold)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun handjetLightTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.handjet_light)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun handjetMediumTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.handjet_medium)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun handjetRegularTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.handjet_regular)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun nunitoSansCondensedExtraBoldTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.nunito_sans_condensed_extra_bold)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun nunitoSansCondensedExtraBoldItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.nunito_sans_condensed_extra_bold_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun nunitoSansCondensedExtraLightTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.nunito_sans_condensed_extra_light)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun nunitoSansCondensedExtraLightItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.nunito_sans_condensed_extra_light_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun nunitoSansCondensedMediumTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.nunito_sans_condensed_medium)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun nunitoSansCondensedMediumItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.nunito_sanst_condensed_medium_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun playfairDisplayBoldTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.playfair_display_bold)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}


@Composable
fun playfairDisplayBoldItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.playfair_display_bold_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}


@Composable
fun playfairDisplayRegularTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.playfair_display_regular)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun playfairDisplayItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.playfair_display_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun playwriteDegrundRegularTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.playwrite_degrund_regular)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun playwriteDegrundThinTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.playwrite_degrund_thin)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayBlackTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_black)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayBlackItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_black_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayExtraLightTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_extra_light)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayExtraLightItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_extra_light_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayMediumTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_medium)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayMediumItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_medium_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayThinTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_thin)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun ralewayThinItalicTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.raleway_thin_italic)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}

@Composable
fun satisfyRegularTextStyle(
    size: Dp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textShadow: Shadow? = null
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }
    return TextStyle(
        fontSize = fontSize,
        color = color,
        fontFamily = FontFamily(Font(R.font.satisfy_regular)),
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textDecoration = textDecoration,
        textAlign = textAlign,
        shadow = textShadow
    )
}