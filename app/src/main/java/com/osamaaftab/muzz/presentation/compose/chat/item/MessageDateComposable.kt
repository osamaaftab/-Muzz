package com.osamaaftab.muzz.presentation.compose.chat.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.osamaaftab.muzz.domain.model.ChatData
import com.osamaaftab.muzz.presentation.theme.MuzzTypography

@Composable
fun MessageDateComposable(date: ChatData.Date) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.value,
            style = MuzzTypography.bodyMedium,
            modifier = Modifier.padding(12.dp)
        )
    }
}