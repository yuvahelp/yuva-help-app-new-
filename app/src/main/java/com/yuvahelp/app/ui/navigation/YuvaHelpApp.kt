package com.yuvahelp.app.ui.navigation

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yuvahelp.app.ui.screens.AboutScreen
import com.yuvahelp.app.ui.screens.ArticleScreen
import com.yuvahelp.app.ui.screens.CategoriesScreen
import com.yuvahelp.app.ui.screens.HomeScreen
import com.yuvahelp.app.ui.screens.LatestUpdatesScreen
import com.yuvahelp.app.ui.screens.SearchScreen
import com.yuvahelp.app.ui.viewmodel.PostViewModel
import com.yuvahelp.app.ui.viewmodel.PostViewModelFactory

sealed class AppDest(val route: String, val label: String) {
    data object Home : AppDest("home", "Home")
    data object Categories : AppDest("categories", "Categories")
    data object Latest : AppDest("latest", "Latest Updates")
    data object Search : AppDest("search", "Search")
    data object About : AppDest("about", "About")
    data object Article : AppDest("article/{id}", "Article") {
        fun withId(id: Long) = "article/$id"
    }
}

@Composable
fun YuvaHelpApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel: PostViewModel = viewModel(factory = PostViewModelFactory(context.applicationContext as Application))

    val tabs = listOf(AppDest.Home, AppDest.Categories, AppDest.Latest, AppDest.Search, AppDest.About)

    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = { navController.navigate(tab.route) },
                        icon = {
                            Icon(
                                imageVector = when (tab) {
                                    AppDest.Home -> Icons.Default.Home
                                    AppDest.Categories -> Icons.Default.List
                                    AppDest.Latest -> Icons.Default.Notifications
                                    AppDest.Search -> Icons.Default.Search
                                    AppDest.About -> Icons.Default.Info
                                    else -> Icons.Default.Home
                                },
                                contentDescription = tab.label
                            )
                        },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { inner ->
        NavHost(navController, startDestination = AppDest.Home.route, modifier = Modifier.padding(inner)) {
            composable(AppDest.Home.route) {
                HomeScreen(viewModel) { postId -> navController.navigate(AppDest.Article.withId(postId)) }
            }
            composable(AppDest.Categories.route) {
                CategoriesScreen(viewModel) { postId -> navController.navigate(AppDest.Article.withId(postId)) }
            }
            composable(AppDest.Latest.route) {
                LatestUpdatesScreen(viewModel) { postId -> navController.navigate(AppDest.Article.withId(postId)) }
            }
            composable(AppDest.Search.route) {
                SearchScreen(viewModel) { postId -> navController.navigate(AppDest.Article.withId(postId)) }
            }
            composable(AppDest.About.route) {
                AboutScreen(onOpenWebsite = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://yuva.help/")))
                })
            }
            composable(
                AppDest.Article.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStack ->
                val id = backStack.arguments?.getLong("id") ?: return@composable
                ArticleScreen(viewModel = viewModel, postId = id)
            }
        }
    }
}
