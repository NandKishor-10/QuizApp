package com.nandkishor.quizapp.presentation.quizscreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.nandkishor.quizapp.presentation.common.Dimensions

@Composable
fun OptionButton(
    option: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.FiveDP)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = Dimensions.OneDP,
                color = if (option == selectedOption) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
            .selectable(
                selected = (option == selectedOption),
                onClick = { onOptionSelected(option) },
                role = Role.RadioButton
            )
            .padding(Dimensions.TenDP),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = ( option == selectedOption ),
            onClick = null
        )
        Spacer(modifier = modifier.padding(Dimensions.FiveDP))
        Text(text = option, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun OptionButtonPrev() {
    var selectedOption by remember{ mutableStateOf("")}
    OptionButton(
        option = "Option",
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it}
    )
}