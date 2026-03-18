package com.example.daysoftheyear.presentation

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class WindowSize(
    val width: WindowType,
    val height: WindowType
)

data class LayoutSpec(
    val columns: Int,
    val cellSize: Dp,
    val padding: Dp,
    val fontSize: TextUnit,
//    val verticalCompression: Float
    val spaceX: Dp,
    val spaceY: Dp,
    val beforeHeight: Dp,
    val afterHeight: Dp
)

enum class WindowType {
    Compact, Medium, Expanded
}


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberWindowSize(configuration: Configuration): WindowSize {
    return WindowSize(
        width = when {
            configuration.screenWidthDp < 600 -> WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowType.Medium
            else -> WindowType.Expanded
        },
        height = when {
            configuration.screenHeightDp < 480 -> WindowType.Compact
            configuration.screenHeightDp < 900 -> WindowType.Medium
            else -> WindowType.Expanded
        }
    )
}

@Composable
fun rememberLayoutSpec(windowSize: WindowSize): LayoutSpec {
    return when (windowSize.width) {
        WindowType.Compact -> {
            when (windowSize.height) {
                WindowType.Compact -> {
                    println("Compact, Compact")
                    LayoutSpec(
                        columns = 20,
                        cellSize = 6.dp,
                        padding = 10.dp,
                        fontSize = 12.sp,
                        spaceX = 12.dp,
                        spaceY = 18.dp,
                        beforeHeight = 200.dp,
                        afterHeight = 300.dp
                    )
                }

                WindowType.Medium -> { /* portrait small*/
                    LayoutSpec(
                        columns = 18,
                        cellSize = 6.dp,
                        padding = 10.dp,
                        fontSize = 12.sp,
                        spaceX = 12.dp,
                        spaceY = 18.dp,
                        beforeHeight = 200.dp,
                        afterHeight = 300.dp
                    )
                }

                WindowType.Expanded -> {  /* portrait myphone*/
                    LayoutSpec(
                        columns = 14,
                        cellSize = 10.dp,
                        padding = 14.dp,
                        fontSize = 18.sp,
                        spaceX = 24.dp,
                        spaceY = 20.dp,
                        beforeHeight = 200.dp,
                        afterHeight = 300.dp
                    )
                }
            }
        }

        WindowType.Medium -> {
            when (windowSize.height) {
                WindowType.Compact -> {/* landscape small*/
                    LayoutSpec(
                        columns = 25,
                        cellSize = 10.dp,
                        padding = 10.dp,
                        fontSize = 12.sp,
                        spaceX = 20.dp,
                        spaceY = 10.dp,
                        beforeHeight = 200.dp,
                        afterHeight = 300.dp
                    )
                }

                WindowType.Medium -> {
                    println("Medium Medium")
                    LayoutSpec(
                        columns = 14,
                        cellSize = 14.dp,
                        padding = 14.dp,
                        fontSize = 14.sp,
                        spaceX = 14.dp,
                        spaceY = 14.dp,
                        beforeHeight = 200.dp,
                        afterHeight = 300.dp
                    )
                }

                WindowType.Expanded -> {/* portrait tab*/
                    LayoutSpec(
                        columns = 18,
                        cellSize = 14.dp,
                        padding = 14.dp,
                        fontSize = 24.sp,
                        spaceX = 16.dp,
                        spaceY = 16.dp,
                        beforeHeight = 200.dp,
                        afterHeight = 300.dp
                    )
                }
            }
        }

        else -> {
            when (windowSize.height) {
                WindowType.Compact -> { /* landscape myphone */
                    println("here")
                    LayoutSpec(
                        columns = 34,
                        cellSize = 5.dp,
                        padding = 6.dp,
                        fontSize = 12.sp,
                        spaceX = 22.dp,
                        spaceY = 20.dp,
                        beforeHeight = 150.dp,
                        afterHeight = 200.dp
                    )
                }

                WindowType.Medium,
                WindowType.Expanded -> {/* landscape tab*/
                    LayoutSpec(
                        columns = 30,
                        cellSize = 14.dp,
                        padding = 14.dp,
                        fontSize = 20.sp,
                        spaceX = 14.dp,
                        spaceY = 20.dp,
                        beforeHeight = 200.dp,
                        afterHeight = 300.dp
                    )
                }
            }
        }
    }
}