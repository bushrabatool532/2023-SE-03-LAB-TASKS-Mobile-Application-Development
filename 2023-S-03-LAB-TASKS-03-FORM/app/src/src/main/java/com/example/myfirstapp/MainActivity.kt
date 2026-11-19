package com.example.myfirstapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import com.example.myfirstapp.ui.theme.MyfirstappTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyfirstappTheme {
                RegistrationScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Hello $name!")
        Text(text = "My first Android app")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        Greeting("Android")
    }
}

@Composable
fun EventSummary(title: String, venue: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Text(text = venue, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun EventSummaryPreview1() {
    EventSummary(title = "Robotics Workshop", venue = "Main Auditorium")
}

@Preview(showBackground = true)
@Composable
fun EventSummaryPreview2() {
    EventSummary(title = "Annual Book Fair", venue = "Central Library Hall")
}
@Composable
fun CampusEventCard(title: String, date: String, venue: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.event_banner),
                contentDescription = "Photo of $title",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = date, style = MaterialTheme.typography.bodyMedium)
                Text(text = venue, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
 @Preview(name = "Normal 360dp", showBackground = true, widthDp = 360)
@Composable
fun CampusEventCardNormalPreview() {
    CampusEventCard(
        title = "Robotics Workshop",
        date = "Sat, 14 Dec 2026",
        venue = "Main Auditorium"
    )
}

@Preview(name = "Narrow 280dp with long text", showBackground = true, widthDp = 280)
@Composable
fun CampusEventCardNarrowPreview() {
    CampusEventCard(
        title = "Inter-University Robotics and Automation Challenge",
        date = "Sat, 14 Dec 2026, 10:00 AM",
        venue = "Central Library Main Hall, Block B"
    )
}

@Preview(
    name = "Dark theme",
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CampusEventCardDarkPreview() {
    MyfirstappTheme(darkTheme = true) {
        CampusEventCard(
            title = "Annual Book Fair",
            date = "Mon, 21 Dec 2026",
            venue = "Central Library"
        )
    }
}
@Preview(showBackground = true, name = "Modifier order")
@Composable
fun ModifierOrderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Background before padding",
            modifier = Modifier
                .background(Color.Yellow)
                .padding(16.dp)
        )
        Text(
            text = "Padding before background",
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Yellow)
        )
    }
}
