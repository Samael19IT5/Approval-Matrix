package com.example.approvalmatrix.data

import com.example.approvalmatrix.model.ApprovalMatrix

data class UiState(
    val approvalMatrixList: MutableList<ApprovalMatrix> = mutableListOf(
        ApprovalMatrix(
            "First",
            "Transfer Online",
            "0",
            "50000",
            1,
            listOf("GROUP MG1,GROUP MG2")
        ),
        ApprovalMatrix(
            "Second",
            "Transfer Online",
            "50000",
            "100000",
            2,
            listOf("GROUP MG1,GROUP MG2,\nGROUP MG3", "GROUP FI1,GROUP FI2,\nGROUP CROSS")
        )
    ),
    val index: Int = -1,
    val item: ApprovalMatrix = ApprovalMatrix(
        "",
        "",
        "",
        "",
        -1,
        listOf()
    )
)

