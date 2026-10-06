package com.curiousapps.myprayer.ui.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen12Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen16Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.DIMEN1F
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen20Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen2Dp
import com.curiousapps.myprayer.util.PRIVACY_POLICY_URL

private val PyriteFontFamily = FontFamily(Font(R.font.pyrite))
private val version = R.string.version
private val versionNumber = R.string.version_number
private val release = R.string.release
private val release_date = R.string.release_date


@Composable
fun AboutScreen() {
    val uriHandler = LocalUriHandler.current

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundWithStones()

            Surface(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(bottom = dimen12Dp),
                color = Color.Blue.copy(alpha = DIMEN1F),
                tonalElevation = dimen2Dp
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = dimen20Dp,
                        vertical = dimen16Dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.curious_custom_apps),
                        style = MaterialTheme.typography.displayLarge.copy(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF004400),
                                    Color(0xFFF1E9FF)
                                )
                            )
                        ),
                        fontFamily = PyriteFontFamily,
                        color = Color.Unspecified
                    )
                    Text(
                        text = stringResource(R.string.email_curiousaps),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF004400)
                    )
                    Text(
                        text = stringResource(R.string.created_by_cca),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF004400)
                    )
                }
            }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimen12Dp),
            color = Color.Blue.copy(alpha = DIMEN1F),
            tonalElevation = dimen2Dp,
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = dimen20Dp,
                    vertical = dimen16Dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { uriHandler.openUri(PRIVACY_POLICY_URL) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(
                        text = stringResource(R.string.privacy_policy),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
                Text(
                    text = "${stringResource(version)} ${stringResource(versionNumber)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = "${stringResource(release)} ${stringResource(release_date)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}
