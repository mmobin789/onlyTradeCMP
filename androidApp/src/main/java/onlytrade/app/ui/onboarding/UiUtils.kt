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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import onlytrade.app.ui.design_system.theme.onlyTradePrimary

@Composable
fun color(light: Long, dark: Long) = Color(if (isSystemInDarkTheme()) dark else light)

@Composable
fun color(light: Color, dark: Color) = if (isSystemInDarkTheme()) dark else light

@Composable
@DrawableRes
fun drawableRes(light: Int, dark: Int) = if (isSystemInDarkTheme()) dark else light
