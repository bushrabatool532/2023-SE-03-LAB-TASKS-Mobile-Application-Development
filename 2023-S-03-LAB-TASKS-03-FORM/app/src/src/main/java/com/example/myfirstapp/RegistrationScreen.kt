package com.example.myfirstapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationScreen() {
    // Form values
    var fullName by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var programme by remember { mutableStateOf<String?>(null) }
    var programmeMenuOpen by remember { mutableStateOf(false) }
    var studyMode by remember { mutableStateOf<String?>(null) }
    var mobileDev by remember { mutableStateOf(false) }
    var webDev by remember { mutableStateOf(false) }
    var testing by remember { mutableStateOf(false) }
    var receiveUpdates by remember { mutableStateOf(false) }
    var studyHours by remember { mutableStateOf(0f) }

    // Form status
    var showErrors by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }
    var submitCount by remember { mutableStateOf(0) }

    val scrollState = rememberScrollState()

    val programmes = listOf(
        "Software Engineering Technology",
        "Computer Science",
        "Information Technology"
    )
    val studyModes = listOf("On campus", "Online")

    // Validation (recomputed on every change)
    val nameError = if (fullName.trim().isEmpty()) "Full name is required" else null
    val rollError = if (rollNumber.trim().isEmpty()) "Roll number is required" else null
    val emailError = if (email.trim().isEmpty()) "Email is required" else null
    val programmeError = if (programme == null) "Please select a programme" else null
    val modeError = if (studyMode == null) "Please select a study mode" else null
    val isValid = nameError == null && rollError == null &&
            emailError == null && programmeError == null && modeError == null

    val interests = listOfNotNull(
        if (mobileDev) "Mobile Development" else null,
        if (webDev) "Web Development" else null,
        if (testing) "Software Testing" else null
    ).ifEmpty { listOf("None") }.joinToString(", ")

    // Scroll after Register is pressed, once the new content is on screen
    LaunchedEffect(submitCount) {
        if (submitCount > 0) {
            if (submitted) {
                scrollState.animateScrollTo(scrollState.maxValue)
            } else {
                scrollState.animateScrollTo(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Student Registration Form",
            style = MaterialTheme.typography.headlineSmall
        )

        // Full name
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it; submitted = false },
            label = { Text("Full name") },
            singleLine = true,
            isError = showErrors && nameError != null,
            supportingText = { if (showErrors && nameError != null) Text(nameError) },
            modifier = Modifier.fillMaxWidth()
        )

        // Roll number
        OutlinedTextField(
            value = rollNumber,
            onValueChange = { rollNumber = it; submitted = false },
            label = { Text("Roll number") },
            singleLine = true,
            isError = showErrors && rollError != null,
            supportingText = { if (showErrors && rollError != null) Text(rollError) },
            modifier = Modifier.fillMaxWidth()
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it; submitted = false },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = showErrors && emailError != null,
            supportingText = { if (showErrors && emailError != null) Text(emailError) },
            modifier = Modifier.fillMaxWidth()
        )

        // Programme dropdown
        Text(text = "Programme", style = MaterialTheme.typography.titleSmall)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { programmeMenuOpen = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(programme ?: "Select programme")
            }
            DropdownMenu(
                expanded = programmeMenuOpen,
                onDismissRequest = { programmeMenuOpen = false }
            ) {
                programmes.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            programme = option
                            programmeMenuOpen = false
                            submitted = false
                        }
                    )
                }
            }
        }
        if (showErrors && programmeError != null) {
            Text(
                text = programmeError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Study mode radio group (only one can be selected)
        Text(text = "Study mode", style = MaterialTheme.typography.titleSmall)
        studyModes.forEach { mode ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = studyMode == mode,
                        onClick = { studyMode = mode; submitted = false },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(selected = studyMode == mode, onClick = null)
                Text(mode)
            }
        }
        if (showErrors && modeError != null) {
            Text(
                text = modeError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Interests (independent checkboxes)
        Text(text = "Interests", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = mobileDev,
                    onValueChange = { mobileDev = it; submitted = false },
                    role = Role.Checkbox
                )
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(checked = mobileDev, onCheckedChange = null)
            Text("Mobile Development")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = webDev,
                    onValueChange = { webDev = it; submitted = false },
                    role = Role.Checkbox
                )
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(checked = webDev, onCheckedChange = null)
            Text("Web Development")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = testing,
                    onValueChange = { testing = it; submitted = false },
                    role = Role.Checkbox
                )
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(checked = testing, onCheckedChange = null)
            Text("Software Testing")
        }

        // Course updates switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = receiveUpdates,
                    onValueChange = { receiveUpdates = it; submitted = false },
                    role = Role.Switch
                )
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Receive course updates")
            Switch(checked = receiveUpdates, onCheckedChange = null)
        }

        // Weekly study hours slider (whole hours 0 to 20)
        Text(text = "Weekly study hours: ${studyHours.toInt()}")
        Slider(
            value = studyHours,
            onValueChange = { studyHours = it; submitted = false },
            valueRange = 0f..20f,
            steps = 19
        )

        // Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                showErrors = true
                submitted = isValid
                submitCount++
            }) {
                Text("Register")
            }
            OutlinedButton(onClick = {
                fullName = ""
                rollNumber = ""
                email = ""
                programme = null
                programmeMenuOpen = false
                studyMode = null
                mobileDev = false
                webDev = false
                testing = false
                receiveUpdates = false
                studyHours = 0f
                showErrors = false
                submitted = false
                submitCount = 0
            }) {
                Text("Reset")
            }
        }

        // Success message and entered values
        if (submitted) {
            Text(
                text = "Registration form completed successfully.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Text("Full name: ${fullName.trim()}")
            Text("Roll number: ${rollNumber.trim()}")
            Text("Email: ${email.trim()}")
            Text("Programme: $programme")
            Text("Study mode: $studyMode")
            Text("Interests: $interests")
            Text("Course updates: ${if (receiveUpdates) "On" else "Off"}")
            Text("Weekly study hours: ${studyHours.toInt()}")
        }
    }
}