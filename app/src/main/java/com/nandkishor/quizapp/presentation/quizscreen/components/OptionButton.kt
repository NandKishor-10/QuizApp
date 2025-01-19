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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import com.nandkishor.quizapp.presentation.common.Dimensions

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
            .padding(Dimensions.FiveDP)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = if (!selected) Dimensions.OneDP else Dimensions.TwoDP,
                color = if (!selected) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .selectable(
                selected = !selected,
                onClick = { if (!selected) onOptionSelected() else onOptionUnselected() },
                role = Role.RadioButton
            )
            .padding(Dimensions.TenDP),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(modifier = modifier.padding(Dimensions.FiveDP))
        Text(text = option, style = MaterialTheme.typography.bodyLarge)
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun OptionButtonPrev() {
//    var selectedOption by remember{ mutableStateOf("")}
//    OptionButton(
//        option = "Option",
//        selectedOption = selectedOption,
//        selected = false,
//    )
//}