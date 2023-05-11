package com.example.approvalmatrix.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.approvalmatrix.R
import com.example.approvalmatrix.model.ApprovalMatrix

@Composable
fun EditScreen(
    index: Int,
    item: ApprovalMatrix,
    onUpdateButton: (Int) -> Unit,
    onSetItem: (ApprovalMatrix) -> Unit,
    onDeleteButton: (Int) -> Unit = {},
    onClickButton: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val featureList = listOf("Default", "Transfer Online")
    val focusManager = LocalFocusManager.current
    var alias by remember { mutableStateOf(item.alias) }
    var feature by remember { mutableStateOf(item.feature) }
    var min by remember { mutableStateOf(item.min) }
    var max by remember { mutableStateOf(item.max) }
    var num by remember { mutableStateOf(item.num.toString()) }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(
        modifier = Modifier
            .fillMaxSize()

            .padding(16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.info),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFED8B00),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
        Divider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(text = stringResource(id = R.string.alias))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = alias,
                singleLine = true,
                onValueChange = { alias = it },
                placeholder = { Text(text = stringResource(id = R.string.input_name)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.feature))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = feature,
                singleLine = true,
                onValueChange = { feature = it },
                enabled = false,
                placeholder = { Text(text = stringResource(id = R.string.select_feature)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFilledSize = coordinates.size.toSize()
                    }
                    .clickable { },
                trailingIcon = {
                    Icon(icon, null, modifier = Modifier.clickable { expanded = !expanded })
                }
            )
            Box {
                DropdownMenu(expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(
                        with(LocalDensity.current) { textFilledSize.width.toDp() }
                    )) {
                    featureList.forEach { label ->
                        DropdownMenuItem(onClick = {
                            feature = label
                            expanded = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = 1.dp, modifier = Modifier.padding(bottom = 16.dp))
            Text(text = stringResource(id = R.string.range_min))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = min,
                singleLine = true,
                onValueChange = { min = it },
                placeholder = { Text(text = stringResource(id = R.string.input_text)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.range_max))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = max,
                singleLine = true,
                onValueChange = { max = it },
                placeholder = { Text(text = stringResource(id = R.string.input_text)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.number_approval))
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = num,
                singleLine = true,
                onValueChange = { num = it },
                placeholder = { Text(text = stringResource(id = R.string.input_number)) },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedButton(
                onClick = {
                    val tmp = ApprovalMatrix(alias, feature, min, max, num.toInt(), listOf())
                    onSetItem(tmp)
                    onUpdateButton(index)
                    onClickButton()
                },
                enabled = alias != "" && min != "" && max != "" && num != "" && feature != "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                ) {
                Text(text = stringResource(id = R.string.update))
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = {
                    onDeleteButton(index)
                    onClickButton()
                },
                enabled = alias != "" || min != "" || max != "" || num != "" || feature != "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        }
    }
}