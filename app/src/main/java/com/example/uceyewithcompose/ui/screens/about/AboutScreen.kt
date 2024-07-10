package com.example.uceyecomposeversion.ui.screens.about

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.uceyewithcompose.R

@Composable
fun AboutScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AppIntroduction()
            Spacer(modifier = Modifier.height(35.dp))

            TeamSection()

            Spacer(modifier = Modifier.height(16.dp))
            ContactUsButton()
        }

    }

}


@Composable
private fun AppIntroduction() {
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "Company Logo",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = stringResource(id = R.string.our_mission),
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = stringResource(id = R.string.about_us_info1),
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(4.dp))

    Image(
        painter = painterResource(id = R.drawable.medicines_collage),
        contentDescription = "Medicine Collage",
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = stringResource(id = R.string.about_us_info2),
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TeamSection() {
    Column {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(id = R.string.team),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = R.string.team_info),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            val drLinCard = TeamMember(
                name = stringResource(id = R.string.dr_lin),
                role = stringResource(id = R.string.dr_lin_info),
                imageRes = R.drawable.dr_lin
            )
            TeamMemberCard(drLinCard)
        }
    }

    val teamMembers = listOf(
        TeamMember(
            stringResource(id = R.string.ayush),
            stringResource(id = R.string.ayush_info),
            R.drawable.ayush
        ),
        TeamMember(
            stringResource(id = R.string.anh),
            stringResource(id = R.string.anh_info),
            R.drawable.anh
        ),
        TeamMember(
            stringResource(id = R.string.lauren),
            stringResource(id = R.string.lauren_info),
            R.drawable.lauren
        ),
        TeamMember(
            stringResource(id = R.string.melika),
            stringResource(id = R.string.med_student),
            R.drawable.melika
        ),
        TeamMember(
            stringResource(id = R.string.omid),
            stringResource(id = R.string.med_student),
            R.drawable.omid
        ),
        TeamMember(
            stringResource(id = R.string.aidin),
            stringResource(id = R.string.med_student),
            R.drawable.aidin
        ),
        TeamMember(
            stringResource(id = R.string.kevin),
            stringResource(id = R.string.med_student),
            R.drawable.kevin
        ),
        TeamMember(
            stringResource(id = R.string.nour),
            stringResource(id = R.string.android_developer),
            R.drawable.nour
        ),
        TeamMember(
            stringResource(id = R.string.senghoung),
            stringResource(id = R.string.android_web_developer),
            R.drawable.senghoung
        ),
        TeamMember(
            stringResource(id = R.string.rohit),
            stringResource(id = R.string.android_developer),
            R.drawable.rohit
        ),
        TeamMember(
            stringResource(id = R.string.raymond),
            stringResource(id = R.string.android_developer),
            R.drawable.raymond
        ),
        TeamMember(
            stringResource(id = R.string.hailey),
            stringResource(id = R.string.hailey_info),
            R.drawable.hailey
        )
    )

    FlowRow(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 2,
        content = {
            teamMembers.forEach { member ->
                TeamMemberCard(member = member)
            }
        })
}


@Composable
fun TeamMemberCard(member: TeamMember) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .padding(8.dp)
            .width(155.dp),
        // border is used to debug the layout
        // border = BorderStroke(width = 2.dp, color = Color.Red)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val rememberItem = remember { member }
            Image(
                painter = painterResource(id = rememberItem.imageRes),
                contentDescription = rememberItem.name,
                modifier = Modifier
                    .height(200.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(70.dp))
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(70.dp)
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = rememberItem.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = rememberItem.role,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsButton() {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }


    Button(
        onClick = {
            showBottomSheet = true
        }, shape = RoundedCornerShape(50)
    ) {
        Text(text = stringResource(id = R.string.contact), fontSize = 16.sp)
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            }, sheetState = sheetState
        ) {
            Text(
                text = stringResource(id = R.string.contact_info),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AboutUsScreenPreview() {
    val navController = rememberNavController()
    AboutScreen()
}