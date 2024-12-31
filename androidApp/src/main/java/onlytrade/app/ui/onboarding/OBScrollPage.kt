package onlytrade.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import onlytrade.app.ui.design.components.DotsIndicator
import onlytrade.app.ui.home.HomeScreen
import onlytrade.app.ui.login.LoginScreen

class OBScrollPage : Screen {

    @Composable
    override fun Content() {
        OnBoardingScreenContent()
    }
}

@Composable
private fun OnBoardingScreenContent(
    nav: Navigator = LocalNavigator.currentOrThrow
) {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        HorizontalPager(
            modifier = Modifier, state = pagerState, beyondViewportPageCount = 1
        ) { page ->

            when (page) {
                0 -> OBPage1 {
                    coroutineScope.launch {
                        pagerState.scrollToPage(1)
                    }
                }

                1 -> OBPage2 {
                    coroutineScope.launch {
                        pagerState.scrollToPage(2)
                    }
                }

                2 -> OBPage3(onLoginClick = { nav.replaceAll(LoginScreen()) },
                    onGetStartedClick = { nav.replaceAll(HomeScreen()) })


            }

        }
        DotsIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            totalDots = 3,
            selectedIndex = pagerState.currentPage,
            selectedColor = MaterialTheme.colorScheme.tertiary,
            unSelectedColor = Color(0xFFC0C0C0)
        )
    }
}

