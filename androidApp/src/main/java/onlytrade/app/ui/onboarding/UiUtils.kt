package onlytrade.app.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun color(light: Long, dark: Long) = Color(if (isSystemInDarkTheme()) dark else light)

@Composable
fun color(light: Color, dark: Color) = if (isSystemInDarkTheme()) dark else light

@Composable
@DrawableRes
fun drawableRes(light: Int, dark: Int) = if (isSystemInDarkTheme()) dark else light

@Composable
fun DotsIndicator(
    modifier: Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .background(shape = RoundedCornerShape(8.dp), color = color(0xFFF4FDFA, 0xFF212322))
            .padding(8.dp)

    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}