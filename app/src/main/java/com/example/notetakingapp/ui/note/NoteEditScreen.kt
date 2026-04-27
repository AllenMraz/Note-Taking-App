package com.example.notetakingapp.ui.note


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notetakingapp.AppUiState
import com.example.notetakingapp.NoteTopAppBar
import com.example.notetakingapp.R
import com.example.notetakingapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import com.example.notetakingapp.ui.AppViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NoteEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_task_title
    const val noteIdArg = "itemId"
    val routeWithArgs = "$route/{$noteIdArg}"
}
//The function that displayes the edite screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    uiState: AppUiState,
    selectTheme: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            NoteTopAppBar(
                title = stringResource(NoteEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                uiState = uiState,
                selectTheme = selectTheme
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NoteEditBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateNote()
                    withContext(Dispatchers.Main) {
                        navigateBack()
                    }
                }

            },
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteNote()
                    withContext(Dispatchers.Main) {
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
        )
    }
}
//The Main body of the edit screen which calls both NoteInputFrom from NoteEntryScreen and DeleteConfirmationDialog
@Composable
private fun NoteEditBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteDetails) -> Unit,
    onSaveClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        NoteInputForm(
            noteDetails = noteUiState.noteDetails,
            onValueChange = onNoteValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button( // button that saves the changes
            onClick = onSaveClick,
            enabled = noteUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
        OutlinedButton( // button that will call the DeleteConfirmationDialog so user can delete entity
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}
//A dialog message to conferm if user wants to delete this entity
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}
