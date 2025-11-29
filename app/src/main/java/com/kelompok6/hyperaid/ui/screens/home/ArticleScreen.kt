package com.kelompok6.hyperaid.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok6.hyperaid.R

data class ArticleSection(
    val heading: String,
    val body: String
)

data class ArticleData(
    val title: String,
    val image: Int,
    val time: String,
    val sections: List<ArticleSection>
)

fun getArticleById(id: Int): ArticleData {
    return when (id) {

        1 -> ArticleData(
            title = "Lifestyle Changes to Combat Hypertension and Long-Term Health Strategies",
            image = R.drawable.artikel1,
            time = "Wed, Jan 18",
            sections = listOf(
                ArticleSection(
                    "WHAT HAPPENED?",
                    """
            Hypertension continues to rise globally, with millions of individuals developing high blood pressure because of unhealthy lifestyle habits. Many patients are unaware of their condition until symptoms begin to appear.
            """.trimIndent()
                ),
                ArticleSection(
                    "WHAT THEY SAID",
                    """
            Health experts emphasize that simple adjustments—such as reducing sodium, exercising regularly, and improving sleep—can significantly lower blood pressure.
            """.trimIndent()
                ),
                ArticleSection(
                    "WHY IT MATTERS",
                    """
            Early prevention lowers the risk of stroke, kidney failure, and long-term cardiovascular damage.
            """.trimIndent()
                )
            )
        )


        2 -> ArticleData(
            title = "Hypertension and Heart Health: What You Need to Know for Better Long-Term Wellness",
            image = R.drawable.artikel2,
            time = "Yesterday",
            sections = listOf(

                ArticleSection(
                    "WHAT HAPPENED?",
                    """
            High blood pressure has increasingly become a major contributor to cardiovascular diseases worldwide. Many individuals struggle to recognize the early symptoms, causing hypertension to remain untreated for long periods and quietly damage the heart.
            
            Over the past decade, cardiologists have emphasized how even small increases in blood pressure can significantly raise the risk of heart enlargement, artery hardening, and circulation problems.
            """.trimIndent()
                ),

                ArticleSection(
                    "WHAT THEY SAID",
                    """
            Cardiologists explain that the heart must pump harder to push blood through constricted or stiffened arteries, which gradually weakens its function. 
            Doctors recommend regular monitoring, dietary adjustments, and consistent physical activity to maintain cardiovascular health.

            They also advise reducing saturated fats, avoiding smoking, and limiting alcohol intake as key preventive strategies. Routine check-ups allow early detection, giving patients a better chance to control hypertension before complications arise.
            """.trimIndent()
                ),

                ArticleSection(
                    "WHY IT MATTERS",
                    """
            Heart disease remains the leading cause of death globally, and hypertension is one of its most powerful risk factors. Identifying and managing high blood pressure early can drastically reduce the risk of stroke, heart attack, and long-term heart failure.

            Individuals who actively manage their blood pressure often experience improved energy levels, better sleep quality, and long-term cardiovascular benefits.
            """.trimIndent()
                ),

                ArticleSection(
                    "EXTRA CONTEXT",
                    """
            Advancements in digital health tools—such as smart watches, mobile tracking apps, and remote consultations—have made it easier for patients to monitor their daily habits and heart health. Many healthcare providers now rely on digital monitoring systems to help patients stay consistent with medication and lifestyle changes.
            """.trimIndent()
                )
            )
        )



        3 -> ArticleData(
            title = "Tech Solutions for Hypertension Management and Early Detection of Cardiovascular Risks",
            image = R.drawable.artikel3,
            time = "2 days ago",
            sections = listOf(

                ArticleSection(
                    "WHAT HAPPENED?",
                    """
            Technology has rapidly transformed the way individuals monitor and control  hypertension. The rise of smart health devices and mobile applications has made it easier for patients to track daily readings, maintain consistency, and recognize patterns in their blood pressure.

            Many people now rely on wearable devices that automatically record heart rate, sleep quality, and stress levels—giving users insights into how their lifestyle affects their hypertension.
            """.trimIndent()
                ),

                ArticleSection(
                    "WHAT THEY SAID",
                    """
            Developers and medical specialists state that tech-driven health tools bring major improvements in awareness and discipline. Automated reminders help users take their medication on time, while analytics features show progress over weeks or months.

            Healthcare professionals report that patients who use digital monitoring tools show better long-term results because they can easily share data with doctors during consultations.
            """.trimIndent()
                ),

                ArticleSection(
                    "WHY IT MATTERS",
                    """
            Early detection is one of the most powerful methods to prevent serious hypertension-related conditions. With constant monitoring, patients gain a clearer understanding of what causes blood pressure spikes.

            This allows individuals to adjust their daily habits—such as diet, exercise, and stress management—to maintain healthier readings.
            """.trimIndent()
                ),

                ArticleSection(
                    "EXTRA CONTEXT",
                    """
            As artificial intelligence continues to evolve, future health devices will be capable of predicting hypertension risk levels using long-term biometric data. 
            Remote patient monitoring is expected to become a common standard in healthcare, allowing early intervention and reducing hospital visits.

            Experts believe technology will become a central part of hypertension care within the next decade.
            """.trimIndent()
                )
            )
        )
        else -> ArticleData(
            title = "Article Not Found",
            image = R.drawable.artikelsatu, // Gambar default
            time = "",
            sections = listOf(ArticleSection("Error", "No article data available."))
        )
    }
}


@Composable
fun ArticleScreen(
    navController: NavController,
    articleId: Int
) {
    val article = getArticleById(articleId)

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F4F7))
        ) {

            // HEADER CARD
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .size(26.dp)
                                    .clickable { navController.popBackStack() }
                            )
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menu",
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Text(
                            text = article.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 32.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = article.time,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "10 min read",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // IMAGE
            item {
                Image(
                    painter = painterResource(id = article.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // CONTENT SECTIONS
            items(article.sections.size) { index ->
                val sec = article.sections[index]

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                ) {

                    // Bold heading
                    Text(
                        text = sec.heading,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.height(8.dp))

                    // Justified body
                    Text(
                        text = sec.body,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color(0xFF444444),
                        textAlign = TextAlign.Justify
                    )
                }
            }

            item { Spacer(Modifier.height(100.dp)) }
        }

        // FLOATING ACTION BAR
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.Black),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconCount(Icons.Default.FavoriteBorder, "2.5k")
                IconCount(Icons.Default.ChatBubbleOutline, "1.9k")
                IconCount(Icons.Default.Send, "700")
            }
        }
    }
}

@Composable
fun IconCount(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 15.sp
        )
    }
}
