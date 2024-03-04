package com.example.repeatsound.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.repeatsound.ScreensName


@Composable
fun MenuScreen(onClickNav: (String) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        TextButton(
            onClick = { onClickNav(ScreensName.gameScreenName) },
            modifier = Modifier
                .width(200.dp)
                .padding(vertical = 20.dp)
                .border(
                    2.dp,
                    Color.DarkGray,
                    RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = "New Game",
                fontSize = 24.sp
            )
        }
        TextButton(
            onClick = { onClickNav(ScreensName.aboutScreenName) },
            modifier = Modifier
                .width(200.dp)
                .border(
                    2.dp,
                    Color.DarkGray,
                    RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = "About Game",
                fontSize = 24.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewMenuScreen() {
    MenuScreen()
}