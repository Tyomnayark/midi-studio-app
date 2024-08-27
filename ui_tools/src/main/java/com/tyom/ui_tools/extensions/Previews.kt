package com.tyom.ui_tools.extensions

import androidx.compose.ui.tooling.preview.Preview

/**
 * Превью соответствует размерам из Figma Phone Small
 * */
@Preview(
    name = "Android small",
    device = "spec:width=360dp,height=640dp,dpi=420,orientation=portrait"
)
annotation class FigmaSmallPreview

/**
 * Превью соответствует размерам из Figma Phone Large
 * */
@Preview(
    name = "Android Large",
    device = "spec:width=390dp,height=844dp,dpi=420,orientation=portrait"
)
annotation class FigmaLargePreview

/**
 * Phones
 * */
@Preview(
    name = "1 - 360x572 - Phone - 1",
    device = "spec:width=360dp,height=572dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 360x615 - Phone - 2",
    device = "spec:width=360dp,height=615dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 360x640 - Phone - 3",
    device = "spec:width=360dp,height=640dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 336x692 - Phone - 4",
    device = "spec:width=336dp,height=692dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 411x731 - Phone - 5",
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 365x776 - Phone - 6",
    device = "spec:width=365dp,height=776dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 384x783 - Phone - 7",
    device = "spec:width=384dp,height=783dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 392x792 - Phone - 8",
    device = "spec:width=392dp,height=792dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 392x823 - Phone - 9",
    device = "spec:width=392dp,height=823dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 390x844 - Phone - 10",
    device = "spec:width=390dp,height=844dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 480x854 - Phone - 11",
    device = "spec:width=480dp,height=854dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 411x891 - Phone - 12",
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "1 - 412x915 - Phone - 13",
    device = "spec:width=412dp,height=915dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
annotation class PhonePreviews

/**
 * Fold
 * */
@Preview(
    name = "2 - 673x841 - Foldable",
    device = "spec:width=673dp,height=841dp,dpi=480,orientation=portrait"
)
annotation class FoldPreviews

/**
 * Table
 * */
@Preview(
    name = "3 - 768x1024 - Table - 1",
    device = "spec:width=768dp,height=1024dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 594x1126 - Table - 2",
    device = "spec:width=594dp,height=1126dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 744x1133 - Table - 3",
    device = "spec:width=744dp,height=1133dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 944x1133 - Table - 4",
    device = "spec:width=944dp,height=1133dp,dpi=420,isRound=false,orientation=portrait"
)
@Preview(
    name = "3 - 744x1280 - Table - 5",
    device = "spec:width=744dp,height=1280dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 760x1280 - Table - 6",
    device = "spec:width=760dp,height=1280dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 889x1280 - Table - 7",
    device = "spec:width=889dp,height=1280dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 760x1333 - Table - 8",
    device = "spec:width=760dp,height=1333dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 960x1440 - Table - 9",
    device = "spec:width=960dp,height=1440dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "3 - 940x1510 - Table - 10",
    device = "spec:width=940dp,height=1510dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "4 - 1200x1600 - Table - 11",
    device = "spec:width=1200dp,height=1600dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "4 - 1480x1970 - Table - 12",
    device = "spec:width=1480dp,height=1970dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Preview(
    name = "4 - 1200x2000 - Table - 13",
    device = "spec:width=1200dp,height=2000dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
annotation class TabletPreviews

/**
 * Превью всех устройств
 * */
@PhonePreviews
@FoldPreviews
@TabletPreviews
annotation class DevicePreviews