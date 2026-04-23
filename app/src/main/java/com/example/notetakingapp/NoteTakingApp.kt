package com.example.notetakingapp





import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notetakingapp.ui.navigation.NoteNavHost

@Composable
fun NoteTakingApp(navController: NavHostController = rememberNavController(),
                  appViewModel: NoteTakingAppViewModel = viewModel(
        factory = NoteTakingAppViewModel.Factory)
 ){

        NoteNavHost(
            navController = navController,
            uiState = appViewModel.appUiState.collectAsState().value,
            selectTheme = appViewModel::selectTheme
        )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    uiState: AppUiState,
    selectTheme: (Boolean) -> Unit
    ){
    CenterAlignedTopAppBar(title = {Text(title)},
        modifier = modifier,
        scrollBehavior =scrollBehavior,
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            Switch(uiState.isDarkMode,
                    onCheckedChange = selectTheme)
        }
    )
}