package onlytrade.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch

class OBScrollPage : Screen {

    @Composable
    override fun Content() {
        val pagerState = rememberPagerState { 3 }
        val coroutineScope = rememberCoroutineScope()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            val (pager, dots) = createRefs()

            HorizontalPager(
                modifier = Modifier
                    .constrainAs(pager) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(dots.bottom)
                    },
                state = pagerState,
                beyondViewportPageCount = 1
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

                    2 -> OBPage3({

                    }, {

                    })


                }

            }
            DotsIndicator(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .constrainAs(dots) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                totalDots = 3,
                selectedIndex = pagerState.currentPage,
                selectedColor = Color(0xFF21D4B4),
                unSelectedColor = Color(0xFFC0C0C0)
            )
        }

    }

}