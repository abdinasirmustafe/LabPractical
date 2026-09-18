package com.example.mystudyplan

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CourseScreen(context)
                    }
                }
            }
        }
    }
}

// ---------- Top-level screen: form + list, combined ----------
@Composable
fun CourseScreen(context: Context) {
    val dbHandler: DBHandler = remember { DBHandler(context) }

    // Bumping this triggers the LaunchedEffect below to re-query the DB,
    // which is how the list "refreshes" after an insert, update, or delete.
    var refreshTrigger by remember { mutableIntStateOf(0) }

    var courseList by remember { mutableStateOf(listOf<Course>()) }
    var editingCourse by remember { mutableStateOf<Course?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(refreshTrigger) {
        courseList = dbHandler.getAllCourses()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp),
    ) {
        AddDataToDatabase(
            context = context,
            dbHandler = dbHandler,
            editingCourse = editingCourse,
            onCourseSaved = {
                editingCourse = null
                refreshTrigger++
            },
            onCancelEdit = {
                editingCourse = null
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Saved Courses",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Option D: Course count display
            Text(
                text = "${courseList.size} course(s) saved",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Option C: Search/filter field
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search courses...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        val filteredCourses = courseList.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.tracks.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true)
        }

        CourseListView(
            courses = filteredCourses,
            onEdit = { course ->
                editingCourse = course
            },
            onDelete = { id ->
                dbHandler.deleteCourse(id)
                if (editingCourse?.id == id) {
                    editingCourse = null
                }
                refreshTrigger++
            }
        )
    }
}

@Composable
fun AddDataToDatabase(
    context: Context,
    dbHandler: DBHandler,
    editingCourse: Course?,
    onCourseSaved: () -> Unit,
    onCancelEdit: () -> Unit
) {
    var courseName by remember { mutableStateOf(TextFieldValue()) }
    var courseDuration by remember { mutableStateOf(TextFieldValue()) }
    var courseTracks by remember { mutableStateOf(TextFieldValue()) }
    var courseDescription by remember { mutableStateOf(TextFieldValue()) }

    // Option B: Pre-fill fields when editing a course
    LaunchedEffect(editingCourse) {
        if (editingCourse != null) {
            courseName = TextFieldValue(editingCourse.name)
            courseDuration = TextFieldValue(editingCourse.duration)
            courseTracks = TextFieldValue(editingCourse.tracks)
            courseDescription = TextFieldValue(editingCourse.description)
        } else {
            courseName = TextFieldValue("")
            courseDuration = TextFieldValue("")
            courseTracks = TextFieldValue("")
            courseDescription = TextFieldValue("")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "SQLite Database in Android",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = courseName,
            onValueChange = { courseName = it },
            placeholder = { Text(text = "Enter your course name") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = courseDuration,
            onValueChange = { courseDuration = it },
            placeholder = { Text(text = "Enter your course duration") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = courseTracks,
            onValueChange = { courseTracks = it },
            placeholder = { Text(text = "Enter your course tracks") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = courseDescription,
            onValueChange = { courseDescription = it },
            placeholder = { Text(text = "Enter your course description") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Option A: Input validation check
            Button(onClick = {
                if (courseName.text.isBlank()) {
                    Toast.makeText(context, "Course name is required", Toast.LENGTH_SHORT).show()
                } else {
                    if (editingCourse != null) {
                        dbHandler.updateCourse(
                            editingCourse.id,
                            courseName.text,
                            courseDuration.text,
                            courseDescription.text,
                            courseTracks.text
                        )
                        Toast.makeText(context, "Course Updated in Database", Toast.LENGTH_SHORT).show()
                    } else {
                        dbHandler.addNewCourse(
                            courseName.text,
                            courseDuration.text,
                            courseDescription.text,
                            courseTracks.text
                        )
                        Toast.makeText(context, "Course Added to Database", Toast.LENGTH_SHORT).show()
                    }
                    courseName = TextFieldValue("")
                    courseDuration = TextFieldValue("")
                    courseTracks = TextFieldValue("")
                    courseDescription = TextFieldValue("")
                    onCourseSaved()
                }
            }) {
                Text(
                    text = if (editingCourse != null) "Update Course" else "Add Course to Database",
                    color = Color.White
                )
            }

            if (editingCourse != null) {
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedButton(onClick = {
                    courseName = TextFieldValue("")
                    courseDuration = TextFieldValue("")
                    courseTracks = TextFieldValue("")
                    courseDescription = TextFieldValue("")
                    onCancelEdit()
                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

// ---------- The list view ----------
@Composable
fun CourseListView(
    courses: List<Course>,
    onEdit: (Course) -> Unit,
    onDelete: (Int) -> Unit
) {
    if (courses.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No courses saved yet.", color = Color.Gray, fontSize = 14.sp)
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(courses, key = { it.id }) { course ->
            CourseRow(
                course = course,
                onEdit = { onEdit(course) },
                onDelete = { onDelete(course.id) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun CourseRow(
    course: Course,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = course.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Duration: ${course.duration}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Tracks: ${course.tracks}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = course.description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Row {
                // Option B: Edit IconButton
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit course",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete course",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}