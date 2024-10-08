package com.example.uceyecomposeversion.ui.screens.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uceyecomposeversion.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutScreen(navController: NavController) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val screenWidth = configuration.screenWidthDp.dp
    val tabItems = listOf(
        TabItem(
            title = stringResource(R.string.our_mission),
            unselectedIcon = Icons.Outlined.Info,
            selectedIcon = Icons.Filled.Info
        ), TabItem(
            title = stringResource(R.string.team),
            unselectedIcon = Icons.Outlined.Person,
            selectedIcon = Icons.Filled.Person
        )
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(
        pagerState.currentPage
    ) {
        selectedTabIndex = pagerState.currentPage
    }

    Scaffold(
        topBar = {
            AboutTopAppBar(
                navController = navController,
                context = context
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(selected = index == selectedTabIndex, onClick = {
                        selectedTabIndex = index
                    }, text = {
                        Text(text = tabItem.title)
                    }, icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) {
                                tabItem.selectedIcon
                            } else tabItem.unselectedIcon, contentDescription = null
                        )
                    })
                }
            }

            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){

                    if (tabItems[index].title == stringResource(R.string.our_mission)) {
                        MissionPager(screenWidth)
                    } else if (tabItems[index].title == stringResource(R.string.team)) {
                        MeetTheTeamPager(screenWidth)
                    }
                }
            }
        }
    }
}

@Composable
private fun MissionPager(screenWidth: Dp) {
    Text(
        text = stringResource(id = R.string.about_us_info1),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Thin
    )

    Spacer(modifier = Modifier.height(8.dp))

    val imageSize = screenWidth * 0.5f
    Image(
        painter = painterResource(id = R.drawable.medicine_collage),
        contentDescription = "Medicine Collage",
        modifier = Modifier
            .size(imageSize)
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(id = R.string.about_us_info2),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Thin
    )
}

@Composable
private fun MeetTheTeamPager(screenWidth: Dp) {
    Text(
        text = stringResource(id = R.string.team_info),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Thin
    )

    Divider(
        thickness = 2.dp, modifier = Modifier.padding(horizontal = 32.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    val teamMembers = listOf(
        TeamMember(
            name = stringResource(id = R.string.dr_lin),
            role = stringResource(id = R.string.dr_lin_info),
            imageRes = R.drawable.dr_lin
        ), TeamMember(
            stringResource(id = R.string.ayush),
            stringResource(id = R.string.ayush_info),
            R.drawable.ayush
        ), TeamMember(
            stringResource(id = R.string.anh),
            stringResource(id = R.string.anh_info),
            R.drawable.anh
        ), TeamMember(
            stringResource(id = R.string.lauren),
            stringResource(id = R.string.lauren_info),
            R.drawable.lauren
        ), TeamMember(
            stringResource(id = R.string.melika),
            stringResource(id = R.string.med_student),
            R.drawable.melika
        ), TeamMember(
            stringResource(id = R.string.omid),
            stringResource(id = R.string.med_student),
            R.drawable.omid
        ), TeamMember(
            stringResource(id = R.string.aidin),
            stringResource(id = R.string.med_student),
            R.drawable.aidin
        ), TeamMember(
            stringResource(id = R.string.kevin),
            stringResource(id = R.string.med_student),
            R.drawable.kevin
        ), TeamMember(
            stringResource(id = R.string.nour),
            stringResource(id = R.string.android_developer),
            R.drawable.nour
        ), TeamMember(
            stringResource(id = R.string.senghoung),
            stringResource(id = R.string.android_web_developer),
            R.drawable.senghoung
        ), TeamMember(
            stringResource(id = R.string.rohit),
            stringResource(id = R.string.android_developer),
            R.drawable.rohit
        ), TeamMember(
            stringResource(id = R.string.raymond),
            stringResource(id = R.string.android_developer),
            R.drawable.raymond
        ), TeamMember(
            stringResource(id = R.string.hailey),
            stringResource(id = R.string.hailey_info),
            R.drawable.hailey
        )
    )
    repeat(teamMembers.size) { index ->
        TeamMemberCard(teamMembers[index], screenWidth)
    }

    Spacer(modifier = Modifier.height(8.dp))

    ContactUsButton()
}

@Composable
fun TeamMemberCard(member: TeamMember, screenWidth: Dp) {
    val imageSize = screenWidth * 0.25f
    val cardWidth = screenWidth * 0.75f
    val cardHeight = cardWidth * 0.5f

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(16.dp)
                .size(
                    width = cardWidth, height = cardHeight
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(member.imageRes),
                    contentDescription = member.name,
                    modifier = Modifier
                        .aspectRatio(9f / 16f)
                        .align(Alignment.CenterVertically)
                        .size(imageSize)
                        .clip(RoundedCornerShape(75.dp))
                        .border(
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
                            shape = RoundedCornerShape(75.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = member.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = member.role,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Thin
                    )
                }
            }
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
        }, shape = RoundedCornerShape(50),
    ) {
        Text(text = stringResource(id = R.string.contact), fontSize = 16.sp)
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val annotatedString = buildAnnotatedString {
                    val emailAddress = "eyedrop.linresearchproject@gmail.com"
                    val loginMessage = stringResource(id = R.string.contact_info, emailAddress)
                    val startIndex = loginMessage.indexOf(emailAddress)
                    val endIndex = startIndex + emailAddress.length
                    append(loginMessage)
                    addStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.surfaceTint,
                            textDecoration = TextDecoration.Underline
                        ),
                        startIndex,
                        endIndex
                    )
                    addStringAnnotation(
                        tag = "EMAIL",
                        annotation = emailAddress,
                        start = startIndex,
                        end = endIndex
                    )
                }
                val context = LocalContext.current

                ClickableText(
                    text = annotatedString,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    onClick = { offset ->
                        // Check if the clicked position has an EMAIL annotation
                        annotatedString.getStringAnnotations(tag = "EMAIL", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                // Open the email client with the email address
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:${annotation.item}")
                                }
                                context.startActivity(intent)
                            }
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AboutTopAppBar(
    navController: NavController,
    context: Context,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Text(
                stringResource(id = R.string.app_name)
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

data class TabItem(
    val title: String, val unselectedIcon: ImageVector, val selectedIcon: ImageVector
)

data class TeamMember(
    val name: String, val role: String, val imageRes: Int
)
