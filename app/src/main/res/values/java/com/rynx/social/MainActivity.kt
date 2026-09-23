package com.rynx.social

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Post(
    val user: String,
    val text: String,
    val likes: Int,
    val comments: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RynxSocialApp()
        }
    }
}

@Composable
fun RynxSocialApp() {
    var selected by remember { mutableIntStateOf(0) }

    val posts = remember {
        mutableStateListOf(
            Post("Rynx", "Welcome to Rynx Social 🚀", 12, 3),
            Post("Nova", "First post on the new app 👀", 21, 7)
        )
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Rynx",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    listOf(
                        "Home",
                        "Search",
                        "Create",
                        "Profile"
                    ).forEachIndexed { index, label ->

                        NavigationBarItem(
                            selected = selected == index,
                            onClick = {
                                selected = index
                            },
                            icon = {
                                Text(
                                    listOf("⌂", "⌕", "+", "●")[index]
                                )
                            },
                            label = {
                                Text(label)
                            }
                        )
                    }
                }
            }
        ) { padding ->

            when (selected) {

                0 -> HomeScreen(
                    posts,
                    Modifier.padding(padding)
                )

                1 -> CenterMessage(
                    "Search is coming soon 🔎",
                    Modifier.padding(padding)
                )

                2 -> CreateScreen(
                    onPost = { text ->
                        if (text.isNotBlank()) {
                            posts.add(
                                0,
                                Post(
                                    "Rynx",
                                    text,
                                    0,
                                    0
                                )
                            )
                        }

                        selected = 0
                    },
                    modifier = Modifier.padding(padding)
                )

                else -> ProfileScreen(
                    Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    posts: List<Post>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(posts) { post ->

            Card(
                Modifier.fillMaxWidth()
            ) {

                Column(
                    Modifier.padding(16.dp)
                ) {

                    Text(
                        post.user,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        Modifier.height(8.dp)
                    )

                    Text(post.text)

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    Text(
                        "♥ ${post.likes}    💬 ${post.comments}"
                    )
                }
            }
        }
    }
}

@Composable
fun CreateScreen(
    onPost: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            "Create a post",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("What's happening?")
            }
        )

        Button(
            onClick = {
                onPost(text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Post")
        }
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Rynx",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text("@rynx")

        Spacer(
            Modifier.height(16.dp)
        )

        Text("0 followers  •  0 following")
    }
}

@Composable
fun CenterMessage(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}
