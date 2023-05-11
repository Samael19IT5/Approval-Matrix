package com.example.approvalmatrix

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.approvalmatrix.ui.AppViewModel
import com.example.approvalmatrix.ui.CreateNewScreen
import com.example.approvalmatrix.ui.EditScreen
import com.example.approvalmatrix.ui.StartScreen


enum class ApprovalMatrixScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Create(title = R.string.app_name),
    Info(title = R.string.app_name)
}

@Composable
fun ApprovalMatrixAppBar(
    currentScreen: ApprovalMatrixScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Box(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(currentScreen.title),
                    modifier = modifier.align(Alignment.Center)
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    modifier = Modifier
                        .alpha(0f)
                        .size(72.dp)
                )
            }
        },
        elevation = 0.dp
    )
}

@Composable
fun ApprovalMatrixApp(modifier: Modifier = Modifier, viewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ApprovalMatrixScreen.valueOf(
        backStackEntry?.destination?.route ?: ApprovalMatrixScreen.Start.name
    )
    Scaffold(
        topBar = {
            ApprovalMatrixAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = ApprovalMatrixScreen.Start.name,
            modifier = modifier
                .padding(innerPadding)
        ) {
            composable(route = ApprovalMatrixScreen.Start.name) {
                StartScreen(
                    matrixList = uiState.approvalMatrixList,
                    onAddButtonClick = { navController.navigate(ApprovalMatrixScreen.Create.name) },
                    onSelectedIndex = { viewModel.setIndex(it) },
                    onSelectedItem = { viewModel.setItem(it) },
                    onClickItem = { navController.navigate(ApprovalMatrixScreen.Info.name) }
                )
            }
            composable(route = ApprovalMatrixScreen.Create.name) {
                CreateNewScreen {
                    viewModel.addToList(it)
                    navController.navigateUp()
                }
            }
            composable(route = ApprovalMatrixScreen.Info.name) {
                EditScreen(
                    index = uiState.index,
                    item = uiState.item,
                    onUpdateButton = { viewModel.updateList(it) },
                    onSetItem = { viewModel.setItem(it) },
                    onDeleteButton = { viewModel.deleteList(it) }
                ) { navController.navigateUp() }
            }
        }
    }
}