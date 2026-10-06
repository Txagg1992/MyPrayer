package com.curiousapps.myprayer.mainComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen12Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen16Dp
import com.curiousapps.myprayer.ui.theme.AppDimens.dimen8Dp

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onPrayerClick: (String) -> Unit = {},
    onRosaryTodayClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onExitClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(horizontal = dimen16Dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))
        Text(stringResource(R.string.my_prayers), modifier = Modifier.padding(dimen16Dp), style = MaterialTheme.typography.titleLarge)
        HorizontalDivider()

        Text(stringResource(R.string.rosary), modifier = Modifier.padding(dimen16Dp), style = MaterialTheme.typography.titleMedium)
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.home)) },
            selected = false,
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            onClick = { onHomeClick() }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.rosary_today)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onRosaryTodayClick() }
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = dimen8Dp))
        Text(stringResource(R.string.rosary_prayers), modifier = Modifier.padding(dimen16Dp), style = MaterialTheme.typography.titleMedium)

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.apostle_s_creed)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Apostle's Creed") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.lord_s_prayer)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Lord's Prayer") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.hail_mary)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Hail Mary") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.glory_be)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Glory Be") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.fatima_prayer)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Fatima Prayer") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.hail_holy_queen)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Hail Holy Queen") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.concluding_prayer)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Concluding Prayer") }
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(stringResource(R.string.saint_s_prayers), modifier = Modifier.padding(dimen16Dp), style = MaterialTheme.typography.titleMedium)

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.prayer_to_st_michael)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Prayer to St. Michael") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.prayer_to_archangel_gabriel)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Prayer to Archangel – Gabriel") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.prayer_to_archangel_raphael)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Prayer to Archangel – Raphael") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.prayer_to_st_christopher_protection)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Prayer to St. Christopher (Protection)") }
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.prayer_to_st_christopher_traveller)) },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Prayer to St. Christopher (Traveller)") }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = dimen8Dp))

        Text(stringResource(R.string.information), modifier = Modifier.padding(dimen16Dp), style = MaterialTheme.typography.titleMedium)
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.app_information)) },
            selected = false,
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            onClick = { onAboutClick() },
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.contact_us)) },
            selected = false,
            icon = { Icon(Icons.Default.Email, contentDescription = null) },
            onClick = { onNavigationClick()},
        )
        NavigationDrawerItem(
            label = { Text(stringResource(R.string.exit_app)) },
            selected = false,
            icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
            onClick = { onExitClick() },
        )
        Spacer(Modifier.height(dimen12Dp))
    }
}