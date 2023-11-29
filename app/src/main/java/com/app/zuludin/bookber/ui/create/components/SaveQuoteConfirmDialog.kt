package com.app.zuludin.bookber.ui.create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveQuoteConfirmDialog(onDismissRequest: () -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val myData = listOf(MyData(0, "Apples"), MyData(1, "Bananas"), MyData(2, "Kiwis"))

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Save Quote", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { onDismissRequest() }) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }

            Text(text = "Giving absolutely everything doesn’t guarantee you get anything but it’s the only chance to get something.")
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            SampleSpinner(
                modifier = Modifier.fillMaxWidth(),
                list = myData,
                preselected = myData.first(),
                onSelectionChanged = {},
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun SampleSpinner(
    list: List<MyData>,
    preselected: MyData,
    onSelectionChanged: (myData: MyData) -> Unit,
    modifier: Modifier = Modifier
) {

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column {
            TextField(
                value = selected.name,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(10.dp)
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                list.forEach { entry ->

                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = entry.name,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .align(Alignment.Start),
                                color = Color.Black
                            )
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpinnerSample_Preview() {
    MaterialTheme {
        val myData = listOf(MyData(0, "Apples"), MyData(1, "Bananas"), MyData(2, "Kiwis"))

        SampleSpinner(
            myData,
            preselected = myData[1],
            onSelectionChanged = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

data class MyData(
    val id: Int,
    val name: String
)