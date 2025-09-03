package com.nsergio.dev.myrickandmortyapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun Preview() {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Prueba preview")
        Text("Prueba preview")
        Text("Prueba preview")
        Text("Prueba preview")
        Text("Prueba preview")
        Text("Prueba preview")
    }

}