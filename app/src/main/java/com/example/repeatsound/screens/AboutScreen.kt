package com.example.repeatsound.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.repeatsound.R

@Composable
fun AboutScreen(
    record: Int = 0,
    onClickNav: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp)
        )
        {
            TextButton(
                onClick = { onClickNav() },
                modifier = Modifier.border(
                    2.dp,
                    Color.Black,
                    RoundedCornerShape(10.dp)
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.back),
                    fontSize = 25.sp
                )
            }
            Text(
                text = stringResource(id = R.string.record) + " $record",
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 150.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(id = R.string.rules),
                fontSize = 25.sp,
                textDecoration = Underline
            )
            Text(
                text = " " + stringResource(id = R.string.rules_1),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = " " + stringResource(id = R.string.rules_2),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = " " + stringResource(id = R.string.rules_3),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.about),
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 24.sp,
                textDecoration = Underline
            )
            Text(
                text = stringResource(id = R.string.about_text),
                fontSize = 20.sp
            )
        }
    }
}


@Preview
@Composable
fun PreviewAboutScreen() {
    AboutScreen()
}