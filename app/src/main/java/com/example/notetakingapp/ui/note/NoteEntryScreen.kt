package com.example.notetakingapp.ui.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notetakingapp.AppUiState
import com.example.notetakingapp.NoteTopAppBar
import com.example.notetakingapp.R
import com.example.notetakingapp.ui.AppViewModelProvider
import com.example.notetakingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object NoteEntryDestination : NavigationDestination {
    override val route = "item_entry"
    override val titleRes = R.string.note_entry_title
}
//Main Function for NoteEntryScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    uiState: AppUiState,
    selectTheme: (Boolean) -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            NoteTopAppBar( //Calls the apps topbar
                title = stringResource(NoteEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                uiState = uiState,
                selectTheme = selectTheme
            )
        }
    ) { innerPadding ->
        NoteEntryBody( //Calls the Main Logic of NoteEntryScreen
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick ={
                coroutineScope.launch{
                    viewModel.saveNote()
                    withContext(Dispatchers.Main) {
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}
// Function creates a new note entity and calls Note InputForm to let the user enter the title and content
@Composable
fun NoteEntryBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        NoteInputForm( // Calls InputForm for input for title and content
            noteDetails = noteUiState.noteDetails,
            onValueChange = onNoteValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button( // Button that saves the infermation put into InputForm
            onClick = onSaveClick,
            enabled = noteUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(stringResource(R.string.save_action))
        }
    }
}
// Form Lay out that contains two outlinedTextFields to input title and content
@Composable
fun NoteInputForm(
    noteDetails: NoteDetails,
    modifier: Modifier = Modifier,
    onValueChange: (NoteDetails) -> Unit= {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
    ){
        OutlinedTextField( // input field for title
            value = noteDetails.title,
            onValueChange = {onValueChange(noteDetails.copy(title = it))},
            label = {Text(stringResource(R.string.Note_title_req))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField( // input field for content
            value = noteDetails.content,
            onValueChange = {onValueChange(noteDetails.copy(content = it))},
            label = {Text(stringResource(R.string.note_content_req))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}