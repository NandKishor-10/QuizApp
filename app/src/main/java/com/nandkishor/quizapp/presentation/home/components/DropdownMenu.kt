package com.nandkishor.quizapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.nandkishor.quizapp.presentation.common.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    label: String = "label",
    defaultValue: String = "",
    isNecessary: Boolean = false,
    lists: List<String>,
    onDropdownClick: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(defaultValue) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = selectedItem,
            onValueChange = { },
            label = { Text(
                text = buildAnnotatedString {
                    append(label)
                    if (isNecessary)
                        withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                }
            ) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                    modifier = Modifier.menuAnchor(type = MenuAnchorType.SecondaryEditable)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .menuAnchor(type = MenuAnchorType.PrimaryEditable),
            singleLine = true,
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = MaterialTheme.shapes.large,
        ) {
            lists.forEachIndexed {index, item ->
                DropdownMenuItem(
                    text = { Text(text = item, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        selectedItem = item
                        onDropdownClick(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DropdownMenuPrev() {
    val lists = remember { mutableStateListOf("item 1", "item 2", "item 3", "item 4", "item 5", "item 6", "item 7", "item 8", "item 9", " item 10") }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(Dimensions.TwentyFiveDP),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DropdownMenu(lists = lists, onDropdownClick = {})
        Spacer(modifier = Modifier.padding(Dimensions.TwentyFiveDP))
        DropdownMenu(lists = lists, onDropdownClick = {})
    }
}