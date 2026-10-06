package com.curiousapps.myprayer

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.curiousapps.myprayer.mainComponents.DrawerContent
import com.curiousapps.myprayer.ui.presentation.AboutScreen
import com.curiousapps.myprayer.ui.presentation.LaunchScreen
import com.curiousapps.myprayer.ui.presentation.PrayerDetailScreen
import com.curiousapps.myprayer.ui.presentation.RosaryScreen
import com.curiousapps.myprayer.ui.presentation.RosaryViewModel
import com.curiousapps.myprayer.ui.theme.AppDimens.zero
import com.curiousapps.myprayer.ui.theme.MyPrayerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPrayerTheme {
                val navController = rememberNavController()
                val viewModel: RosaryViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState(RosaryViewModel.RosaryState())

                AppDrawer(
                    onPrayerClick = { prayerName ->
                        val rosaryIndex = state.displayPrayers.indexOfFirst { it.prayerName == prayerName }
                        if (rosaryIndex >= zero) {
                            navController.navigate("prayer_detail/$rosaryIndex")
                        } else {
                            val saintIndex = state.saintPrayers.indexOfFirst { it.prayerName == prayerName }
                            if (saintIndex >= zero) {
                                navController.navigate("saint_prayer_detail/$saintIndex")
                            }
                        }
                    },
                    onHomeClick = {
                        navController.navigate("launch") {
                            popUpTo("launch") { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onRosaryTodayClick = {
                        navController.navigate("rosary_list") {
                            popUpTo("rosary_list") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onAboutClick = {
                        navController.navigate("about") {
                            launchSingleTop = true
                        }
                    },
                    onExitClick = {
                        finish()
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "launch",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("launch") {
                            LaunchScreen()
                        }
                        composable("rosary_list") {
                            RosaryScreen(
                                onNavigateToPrayer = { index ->
                                    navController.navigate("prayer_detail/$index")
                                }
                            )
                        }
                        composable("about") {
                            AboutScreen()
                        }
                        composable(
                            route = "prayer_detail/{prayerIndex}",
                            arguments = listOf(
                                navArgument("prayerIndex") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("prayerIndex") ?: zero
                            PrayerDetailScreen(
                                startIndex = index,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable(
                            route = "saint_prayer_detail/{prayerIndex}",
                            arguments = listOf(
                                navArgument("prayerIndex") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("prayerIndex") ?: zero
                            PrayerDetailScreen(
                                startIndex = index,
                                prayersOverride = state.saintPrayers,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    showTopBar: Boolean = true,
    onPrayerClick: (String) -> Unit = {},
    onHomeClick: () -> Unit = {},
    onRosaryTodayClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onExitClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val supportEmail = stringResource(R.string.support_email_address)
    val contactEmailSubject = stringResource(R.string.contact_email_subject)
    val contactEmailChooserTitle = stringResource(R.string.contact_email_chooser_title)
    val noEmailAppFound = stringResource(R.string.no_email_app_found)
    var showContactDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onPrayerClick = { name ->
                        scope.launch { drawerState.close() }
                        onPrayerClick(name)
                    },
                    onRosaryTodayClick = {
                        scope.launch { drawerState.close() }
                        onRosaryTodayClick()
                    },
                    onHomeClick = {
                        scope.launch { drawerState.close() }
                        onHomeClick()
                    },
                    onAboutClick = {
                        scope.launch { drawerState.close() }
                        onAboutClick()
                    },
                    onExitClick = {
                        scope.launch { drawerState.close() }
                        onExitClick()
                    },
                    onContactUsClick = {
                        scope.launch { drawerState.close() }
                        showContactDialog = true
                    }
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                if (showTopBar) {
                    val appBarContentColor = Color.DarkGray
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_name), color = appBarContentColor) },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = stringResource(R.string.menu),
                                    tint = appBarContentColor
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0x8EFFE26D),
                            titleContentColor = appBarContentColor,
                            navigationIconContentColor = appBarContentColor
                        )
                    )
                }
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }

    if (showContactDialog) {
        AlertDialog(
            onDismissRequest = { showContactDialog = false },
            title = { Text(text = stringResource(R.string.contact_us)) },
            text = { Text(text = stringResource(R.string.contact_us_dialog_message, supportEmail)) },
            confirmButton = {
                Button(
                    onClick = {
                        showContactDialog = false
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                            putExtra(Intent.EXTRA_SUBJECT, contactEmailSubject)
                        }
                        val chooserIntent = Intent.createChooser(emailIntent, contactEmailChooserTitle)
                        try {
                            context.startActivity(chooserIntent)
                        } catch (exception: ActivityNotFoundException) {
                            Toast.makeText(context, noEmailAppFound, Toast.LENGTH_LONG).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(text = stringResource(R.string.open_email_app), color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showContactDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(text = stringResource(R.string.cancel), color = Color.White)
                }
            }
        )
    }
}