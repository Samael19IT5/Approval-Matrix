package com.example.approvalmatrix.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.approvalmatrix.R
import com.example.approvalmatrix.model.ApprovalMatrix

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    matrixList: List<ApprovalMatrix>,
    onAddButtonClick: () -> Unit,
    onSelectedIndex: (Int) -> Unit,
    onSelectedItem: (ApprovalMatrix) -> Unit,
    onClickItem: () -> Unit = {},
) {
    val approvals by remember { mutableStateOf(matrixList) }
    var ckDefault by remember { mutableStateOf(false) }
    var ckOnline by remember { mutableStateOf(false) }
    Box(modifier = Modifier.background(color = Color(0xFFED8B00))) {
        Card(
            shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.End
            ) {
                CreateNewButton(
                    labelResourceInt = R.string.add_approval_matrix,
                    onClick = { onAddButtonClick() })
                Divider(thickness = 1.dp, modifier = modifier.padding(vertical = 16.dp))
                ApprovalMatrixType(name = R.string.default_type) { ckDefault = !ckDefault }
                Spacer(modifier = Modifier.height(16.dp))
                ApprovalMatrixType(name = R.string.online_type) { ckOnline = !ckOnline }
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
                    itemsIndexed(approvals) { index, item ->
                        if (item.feature == stringResource(id = R.string.default_type) && ckDefault ||
                            item.feature == stringResource(id = R.string.online_type) && ckOnline
                        ) {
                            ApprovalMatrixItem(
                                item = item,
                                onClick = {
                                    onSelectedIndex(index)
                                    onSelectedItem(item)
                                    onClickItem()
                                })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun CreateNewButton(
    @StringRes labelResourceInt: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF171C8F))

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(labelResourceInt))

        }
    }
}

@Composable
fun ApprovalMatrixType(
    @StringRes name: Int,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    val border = if (expanded) {
        BorderStroke(1.dp, color = Color(0xFFED8B00))
    } else {
        null
    }
    val color = if (expanded) {
        Color(0xFFED8B00)
    } else {
        Color.Black
    }
    val divide = if (expanded) {
        Color(0xFFED8B00)
    } else {
        Color(0xFFD1D2D4)
    }
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        border = border,
        modifier = Modifier.height(60.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = stringResource(id = name), color = color)
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    color = divide,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = name), color = color)
            }
            IconButton(
                onClick = {
                    onClick()
                    expanded = !expanded
                }) {
                Icon(
                    imageVector = icon,
                    tint = color,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun ApprovalMatrixItem(
    item: ApprovalMatrix,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var i = 1
    Column(
        modifier = Modifier
            .border(width = 2.dp, color = Color(0xFFD1D2D4), shape = RoundedCornerShape(15.dp))
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(id = R.string.limit_range), fontSize = 10.sp)
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.min),
                    fontSize = 10.sp,
                    color = Color(0xFF171C8F)
                )
                Text(
                    text = stringResource(id = R.string.max),
                    fontSize = 10.sp,
                    color = Color(0xFF171C8F)
                )
            }
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = item.min,
                    fontSize = 10.sp,
                    color = Color(0xFF171C8F),
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    text = item.max,
                    fontSize = 10.sp,
                    color = Color(0xFF171C8F),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
        Divider(thickness = 1.dp, modifier = modifier.padding(vertical = 8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(id = R.string.number_approval), fontSize = 10.sp)
            Text(text = item.num.toString(), color = Color(0xFF171C8F), fontSize = 10.sp)
        }
        Divider(thickness = 1.dp, modifier = modifier.padding(vertical = 8.dp))
        for (approver in item.approvers) {
            key(approver) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.approver) + " " + i,
                        fontSize = 10.sp
                    )
                    Text(
                        text = approver,
                        color = Color(0xFF171C8F),
                        fontSize = 10.sp,
                        textAlign = TextAlign.End
                    )
                }
            }
            i++
        }
    }
}


