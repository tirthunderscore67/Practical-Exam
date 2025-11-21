    package com.example.techconference

    import android.os.Bundle
    import android.widget.Toast
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.LazyRow
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Button
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                TechConferenceApp()
            }
        }
    }

    /* ---------- DATA ---------- */

    enum class SessionCategory { ALL, KEYNOTE, WORKSHOP, NETWORKING }

    data class Speaker(
        val name: String,
        val role: String
    )

    data class Session(
        val time: String,
        val title: String,
        val category: SessionCategory
    )
    data class Review(
        val name: String,
        val rating: Int,
        val comment: String
    )

    val keynoteReviews = listOf(
        Review("Amit Sharma", 5, "Amazing keynote! Very insightful and energetic."),
        Review("Neha Patel", 4, "Good session, learned a lot about Android trends."),
        Review("Rohan Desai", 5, "Speaker was fantastic and very motivating."),
        Review("Priya Nair", 4, "Great content, could have been a bit longer.")
    )


    private val speakers = listOf(
        Speaker("Dr. Emily Chen", "AI Researcher"),
        Speaker("Jake Wharton", "Android GDE"),
        Speaker("Sarah Connor", "Security Expert")
    )

    private val sessions = listOf(
        Session("11:30 AM", "Kotlin Multiplatform Workshop", SessionCategory.WORKSHOP),
        Session("1:00 PM", "Keynote: Future of Android", SessionCategory.KEYNOTE),
        Session("3:00 PM", "Networking with Speakers", SessionCategory.NETWORKING),
        Session("4:30 PM", "Compose for Large Apps", SessionCategory.WORKSHOP)
    )

    /* ---------- ROOT COMPOSABLE ---------- */

    @Composable
    fun TechConferenceApp() {
        val context = LocalContext.current

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF050814) // dark background
        ) {
            Scaffold(
                containerColor = Color(0xFF050814),
                bottomBar = {
                    BottomActions(
                        onWatchLive = {
                            Toast.makeText(context, "Watch Live clicked", Toast.LENGTH_SHORT).show()
                        },
                        onCalendar = {
                            Toast.makeText(context, "Calendar clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            ) { innerPadding ->
                ConferenceHomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    onSpeakerClick = { speaker ->
                        Toast.makeText(
                            context,
                            "Speaker: ${speaker.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    /* ---------- HOME SCREEN ---------- */

    @Composable
    fun ConferenceHomeScreen(
        modifier: Modifier = Modifier,
        onSpeakerClick: (Speaker) -> Unit
    ) {
        var selectedCategory by remember { mutableStateOf(SessionCategory.ALL) }

        val filteredSessions = remember(selectedCategory) {
            if (selectedCategory == SessionCategory.ALL) sessions
            else sessions.filter { it.category == selectedCategory }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            // Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF222233)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Conference Banner",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            // Title + description
            item {
                Text(
                    text = "Tech Conference 2025",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Mehsana, Gujarat | 2.5 km away",
                    color = Color(0xFFBBBBBB),
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Join us for a deep dive into the future of technology.",
                    color = Color(0xFFCCCCCC),
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Live Updates Active",
                        color = Color(0xFF55FF55),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.height(20.dp))
            }

            // Featured speakers
            item {
                Text(
                    text = "Featured Speakers",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(12.dp))
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(speakers) { speaker ->
                        SpeakerCard(
                            speaker = speaker,
                            onClick = { onSpeakerClick(speaker) }
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            // Schedule header
            item {
                Text(
                    text = "Schedule",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(12.dp))
            }

            // Filter buttons row
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ScheduleFilterChip(
                        text = "All",
                        selected = selectedCategory == SessionCategory.ALL
                    ) { selectedCategory = SessionCategory.ALL }

                    ScheduleFilterChip(
                        text = "Keynote",
                        selected = selectedCategory == SessionCategory.KEYNOTE
                    ) { selectedCategory = SessionCategory.KEYNOTE }

                    ScheduleFilterChip(
                        text = "Workshop",
                        selected = selectedCategory == SessionCategory.WORKSHOP
                    ) { selectedCategory = SessionCategory.WORKSHOP }

                    ScheduleFilterChip(
                        text = "Networking",
                        selected = selectedCategory == SessionCategory.NETWORKING
                    ) { selectedCategory = SessionCategory.NETWORKING }
                }
                Spacer(Modifier.height(16.dp))
            }

            // Sessions list
            items(filteredSessions) { session ->
                if (session.category == SessionCategory.KEYNOTE) {
                    KeynoteSessionCard(session)
                } else {
                    SessionCard(session)
                }
                Spacer(Modifier.height(10.dp))
            }
            // ⭐ KEYNOTE special UI (always shows when keynote tab is selected)
            item {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Reviews",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
            }

            items(keynoteReviews) { review ->
                ReviewCard(review)
                Spacer(Modifier.height(10.dp))
            }
        }
    }

    /* ---------- SMALL UI PARTS ---------- */

    @Composable
    fun SpeakerCard(
        speaker: Speaker,
        onClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .width(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF191A2A))
                .clickable { onClick() }
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF5C6BFF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = speaker.name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = speaker.name,
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = speaker.role,
                color = Color(0xFFB0B3C0),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    fun ScheduleFilterChip(
        text: String,
        selected: Boolean,
        onClick: () -> Unit
    ) {
        val bg = if (selected) Color(0xFF5C6BFF) else Color(0xFF1B1D2A)
        val content = if (selected) Color.White else Color(0xFFCCCCCC)

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(bg)
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text.uppercase(), color = content, fontSize = 11.sp)
        }
    }

    @Composable
    fun SessionCard(session: Session) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF191A2A))
                .padding(12.dp)
        ) {
            Text(
                text = session.time,
                color = Color(0xFF9FA4FF),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = session.title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = session.category.name.lowercase().replaceFirstChar { it.uppercase() },
                color = Color(0xFFB0B3C0),
                fontSize = 12.sp
            )
        }
    }

    @Composable
    fun BottomActions(
        onWatchLive: () -> Unit,
        onCalendar: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF050814))
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onWatchLive
            ) {
                Text(text = "Watch Live")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = onCalendar
            ) {
                Text(text = "Calendar")
            }
        }
    }

    @Composable
    fun KeynoteSessionCard(session: Session) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF2C2356)) // purple-ish keynote bg
                .padding(14.dp)
        ) {
            // Top row: "Keynote" badge + time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFFFC857)) // yellow badge
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "KEYNOTE",
                        color = Color(0xFF1B1336),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = session.time,
                    color = Color(0xFFB8B8FF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(8.dp))

            // Title
            Text(
                text = session.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            // Small description line (you can change text)
            Text(
                text = "Main stage • Hall A",
                color = Color(0xFFDDDDEE),
                fontSize = 12.sp
            )

            Spacer(Modifier.height(10.dp))

            // Bottom row: Join button + status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* TODO: handle join keynote click */ },
                    modifier = Modifier
                        .height(36.dp)
                ) {
                    Text(text = "Join Keynote")
                }

                Text(
                    text = "Starting soon",
                    color = Color(0xFF88FFB2),
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    fun ReviewCard(review: Review) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE6E2FF).copy(alpha = 0.2f))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF5C6BFF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = review.name.first().toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = review.name,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(6.dp))
                    repeat(5) { i ->
                        Text(
                            text = if (i < review.rating) "★" else "☆",
                            color = Color(0xFFFFD700)
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = review.comment,
                    color = Color(0xFFB0B3C0),
                    fontSize = 13.sp
                )
            }
        }
    }