package com.example.repeatsound.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exercise03.Event
import com.example.repeatsound.R
import com.example.repeatsound.ScreenViewModel
import com.example.repeatsound.State
import com.example.repeatsound.ui.theme.Blue
import com.example.repeatsound.ui.theme.LightGreen
import com.example.repeatsound.ui.theme.Orange
import com.example.repeatsound.ui.theme.Pink80


@Composable
fun GameScreen(
    mv: ScreenViewModel,
    changeRecord: (Int) -> Unit = {},
    onClickNav: () -> Unit
) {
    val uiState by mv.state.collectAsState()
    LaunchedEffect(Unit) {
        mv.sendEvent(Event.StartGame)
    }
    if (!uiState.freeGame) {
        Column {
            TextButton(onClick = { onClickNav() }) {
                Text(text = stringResource(R.string.menu), fontSize = 20.sp)
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    )
    {
        if (!uiState.freeGame) {
            Text(
                text = stringResource(R.string.level) + " ${uiState.level}",
                fontSize = 30.sp
            )
        } else {
            TextButton(onClick = {
                mv.sendEvent(
                    Event.ChangeMode(State.GameMode.GAME)
                )
            })
            {
                Text("<-", fontSize = 20.sp)
            }
        }
        Text(text = stringResource(R.string.record) + " ${uiState.record}", fontSize = 30.sp)
    }

    Column(
        modifier = Modifier.padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { mv.sendEvent(Event.ButtonClicked(1)) },
                enabled = uiState.doButtonsEnabled,
                shape = RoundedCornerShape(0),
                modifier = Modifier.size(width = 160.dp, height = 160.dp),
                colors = ButtonDefaults.buttonColors(Pink80),
            )
            {
                Text(
                    stringResource(id = R.string.sound_do), fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { mv.sendEvent(Event.ButtonClicked(2)) },
                enabled = uiState.reButtonsEnabled,
                shape = RoundedCornerShape(0),
                modifier = Modifier.size(width = 160.dp, height = 160.dp),
                colors = ButtonDefaults.buttonColors(Orange)
            )
            {
                Text(
                    stringResource(id = R.string.sound_re), fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { mv.sendEvent(Event.ButtonClicked(3)) },
                enabled = uiState.miButtonsEnabled,
                modifier = Modifier.size(width = 160.dp, height = 160.dp),
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(Blue),
            )
            {
                Text(
                    stringResource(id = R.string.sound_mi), fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )

            }
            Button(
                onClick = { mv.sendEvent(Event.ButtonClicked(4)) },
                enabled = uiState.faButtonsEnabled,
                shape = RoundedCornerShape(0),
                modifier = Modifier.size(width = 160.dp, height = 160.dp),
                colors = ButtonDefaults.buttonColors(LightGreen)
            )
            {
                Text(
                    stringResource(id = R.string.sound_fa), fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        if (uiState.wrongSound) {
            changeRecord(uiState.record)
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(text = stringResource(id = R.string.game_over))
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.your_record) + " ${uiState.level}",
                        fontSize = 20.sp,
                    )
                },
                confirmButton = {
                    TextButton(onClick = { mv.sendEvent(Event.Restart) }) {
                        Text(stringResource(id = R.string.restart), fontSize = 20.sp)
                    }
                    TextButton(onClick = {
                        mv.sendEvent(
                            Event.ChangeMode(State.GameMode.FREE)
                        )
                    }
                    ) {
                        Text(stringResource(id = R.string.free_game), fontSize = 20.sp)
                    }
                }
            )
        }
    }
}

