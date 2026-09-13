package com.curiousapps.myprayer.mainComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.curiousapps.myprayer.R

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {},
    onPrayerClick: (String) -> Unit = {},
    onRosaryTodayClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))
        Text("My Prayers", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        HorizontalDivider()

        Text("Rosary", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
        NavigationDrawerItem(
            label = { Text("Rosary Today") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onRosaryTodayClick() }
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Rosary Prayers", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)

        NavigationDrawerItem(
            label = { Text("Apostle's Creed") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Apostle's Creed") }
        )
        NavigationDrawerItem(
            label = { Text("Lord's Prayer") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Lord's Prayer") }
        )
        NavigationDrawerItem(
            label = { Text("Hail Mary") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Hail Mary") }
        )
        NavigationDrawerItem(
            label = { Text("Glory Be") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Glory Be") }
        )
        NavigationDrawerItem(
            label = { Text("Fatima Prayer") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Fatima Prayer") }
        )
        NavigationDrawerItem(
            label = { Text("Hail Holy Queen") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onPrayerClick("Hail Holy Queen") }
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Saint's Prayers", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)

        NavigationDrawerItem(
            label = { Text("Prayer to St. Michael") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onNavigationClick()}
        )
        NavigationDrawerItem(
            label = { Text("Prayer to Archangel – Gabriel") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onNavigationClick()}
        )
        NavigationDrawerItem(
            label = { Text("Prayer to Archangel – Raphael") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onNavigationClick()}
        )
        NavigationDrawerItem(
            label = { Text("Prayer to St. Christopher \n (Protection)") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onNavigationClick()}
        )
        NavigationDrawerItem(
            label = { Text("Prayer to St. Christopher \n (Traveller)") },
            selected = false,
            icon = { Icon(painterResource(id = R.drawable.celtic_cross_20) ,contentDescription = null) },
            onClick = { onNavigationClick()}
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text("Information", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
        NavigationDrawerItem(
            label = { Text("App Information") },
            selected = false,
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            onClick = { onNavigationClick()},
        )
        NavigationDrawerItem(
            label = { Text("Contact Us") },
            selected = false,
            icon = { Icon(Icons.Default.Email, contentDescription = null) },
            onClick = { onNavigationClick()},
        )
        Spacer(Modifier.height(12.dp))
    }
}