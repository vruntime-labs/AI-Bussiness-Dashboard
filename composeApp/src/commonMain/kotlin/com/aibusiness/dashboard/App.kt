import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize().background(Color(0xFFF3F4F6))) {
            // 1. Sidebar Navigation
            Sidebar()

            // 2. Main Dashboard Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Dashboard User",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // KPI Stat Cards Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    KpiCard(title = "Earning", value = "$ 628", backgroundColor = Color(0xFF1E293B), textColor = Color.White)
                    KpiCard(title = "Share", value = "2434", backgroundColor = Color.White, textColor = Color(0xFF1F2937))
                    KpiCard(title = "Likes", value = "1259", backgroundColor = Color.White, textColor = Color(0xFF1F2937))
                    KpiCard(title = "Rating", value = "8.5", backgroundColor = Color.White, textColor = Color(0xFF1F2937))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Chart Placeholders
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ChartCard(title = "Performance Breakdown (Bar Chart)")
                        ChartCard(title = "Live AI Model Accuracy (Line Chart)")
                    }

                    // Side Donut / Summary Card
                    Card(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Accuracy Score", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("45%", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0284C7))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Model running 24x7", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Sidebar() {
    Column(
        modifier = Modifier
            .width(220.dp)
            .fillMaxHeight()
            .background(Color(0xFF1E293B))
            .padding(16.dp)
    ) {
        Text(
            text = "JOHN DON",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "johndon@company.com",
            color = Color.LightGray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        val navItems = listOf("Home", "File", "Messages", "Notification", "Location", "Graph")
        for (item in navItems) {
            Text(
                text = item,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

@Composable
fun RowScope.KpiCard(title: String, value: String, backgroundColor: Color, textColor: Color) {
    Card(
        modifier = Modifier.weight(1f).height(90.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 12.sp, color = textColor.copy(alpha = 0.7f))
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}

@Composable
fun ColumnScope.ChartCard(title: String) {
    Card(
        modifier = Modifier.fillMaxWidth().weight(1f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(title, color = Color.Gray, fontWeight = FontWeight.Medium)
        }
    }
}
