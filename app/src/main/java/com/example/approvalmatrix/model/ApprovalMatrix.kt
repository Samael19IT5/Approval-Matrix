package com.example.approvalmatrix.model

class ApprovalMatrix(
    var alias: String,
    var feature: String,
    var min: String,
    var max: String,
    var num: Int,
    var approvers: List<String>
)