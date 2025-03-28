package com.nandkishor.quizapp.presentation.quiz.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun OptionButton(
    option: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    onOptionUnselected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = if (!selected) 1.dp else 2.dp,
                color = if (!selected) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .clickable{
                if (selected) onOptionUnselected()
                else onOptionSelected()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(modifier = modifier.padding(5.dp))
        Text(text = option, style = MaterialTheme.typography.bodyLarge)
    }
}