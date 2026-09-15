package com.curiousapps.myprayer

import android.os.Bundle
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.curiousapps.myprayer.mainComponents.DrawerContent
import com.curiousapps.myprayer.ui.presentation.PrayerDetailScreen
import com.curiousapps.myprayer.ui.presentation.RosaryScreen
import com.curiousapps.myprayer.ui.presentation.RosaryViewModel
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
                        val index = state.displayPrayers.indexOfFirst { it.prayerName == prayerName }
                        if (index >= 0) navController.navigate("prayer_detail/$index")
                    },
                    onRosaryTodayClick = {
                        navController.navigate("rosary_list") {
                            popUpTo("rosary_list") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "rosary_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("rosary_list") {
                            RosaryScreen(
                                onNavigateToPrayer = { index ->
                                    navController.navigate("prayer_detail/$index")
                                }
                            )
                        }
                        composable(
                            route = "prayer_detail/{prayerIndex}",
                            arguments = listOf(
                                navArgument("prayerIndex") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("prayerIndex") ?: 0
                            PrayerDetailScreen(
                                startIndex = index,
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
    onPrayerClick: (String) -> Unit = {},
    onRosaryTodayClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                    onNavigationClick = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Prayer App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFFE26D)
                    )
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}