package com.example.notetakingapp.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notetakingapp.home.HomeDestination
import com.example.notetakingapp.home.HomeScreen
import com.example.notetakingapp.ui.note.NoteEditDestination
import com.example.notetakingapp.ui.note.NoteEditScreen
import com.example.notetakingapp.ui.note.NoteEntryDestination
import com.example.notetakingapp.ui.note.NoteEntryScreen


@Composable
fun NoteNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,  startDestination = HomeDestination.route, modifier = modifier
    ) {
        composable(route = HomeDestination.route){
            HomeScreen(navigateToItemEntry = { navController.navigate(NoteEntryDestination.route)},
                navigateToItemUpdate ={
                    navController.navigate("${NoteEditDestination.route}/${it}")
                })
        }
        composable(route = NoteEntryDestination.route) {
            NoteEntryScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = NoteEditDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteEditDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}