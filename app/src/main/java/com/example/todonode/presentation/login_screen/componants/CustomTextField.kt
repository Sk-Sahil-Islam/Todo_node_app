package com.example.todonode.presentation.login_screen.componants

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.todonode.R

@Composable
fun EmailTextField(
    email: String,
    label: @Composable() (() -> Unit)? = null,
    primaryColor: Color,
    onBackgroundColor: Color,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable() (() -> Unit)? = null,
    isError: Boolean,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = email,
        isError = isError,
        onValueChange = { onValueChange(it) },
        label = label,
        leadingIcon = leadingIcon,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = onBackgroundColor,
            focusedTextColor = onBackgroundColor,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = primaryColor,
            focusedLabelColor = primaryColor,
            cursorColor = primaryColor,
            unfocusedIndicatorColor = onBackgroundColor,
            unfocusedLabelColor = onBackgroundColor,
            unfocusedLeadingIconColor = onBackgroundColor,
            unfocusedPlaceholderColor = onBackgroundColor,
            unfocusedPrefixColor = onBackgroundColor,
            unfocusedSuffixColor = onBackgroundColor,
            unfocusedTrailingIconColor = onBackgroundColor,
            focusedLeadingIconColor = onBackgroundColor,
            focusedTrailingIconColor = onBackgroundColor,
            errorLeadingIconColor = onBackgroundColor,
            errorTrailingIconColor = onBackgroundColor
        )
    )

}

@Composable
fun PasswordTextField(
    password: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    primaryColor: Color,
    onBackgroundColor: Color,
    onVisibilityClick: () -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = password,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = "Password"
            )
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(start = 12.dp),
                imageVector = Icons.Outlined.Lock,
                contentDescription = "password"
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val description = if (passwordVisible) "Hide password"
            else "show password"
            IconButton(onClick = onVisibilityClick) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisible) R.drawable.ic_visibility_off
                        else R.drawable.ic_visibility_on
                    ), contentDescription = description
                )
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = onBackgroundColor,
            focusedTextColor = onBackgroundColor,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = primaryColor,
            focusedLabelColor = primaryColor,
            cursorColor = primaryColor,
            unfocusedIndicatorColor = onBackgroundColor,
            unfocusedLabelColor = onBackgroundColor,
            unfocusedLeadingIconColor = onBackgroundColor,
            unfocusedPlaceholderColor = onBackgroundColor,
            unfocusedPrefixColor = onBackgroundColor,
            unfocusedSuffixColor = onBackgroundColor,
            unfocusedTrailingIconColor = onBackgroundColor,
            focusedLeadingIconColor = onBackgroundColor,
            focusedTrailingIconColor = onBackgroundColor,
            errorLeadingIconColor = onBackgroundColor,
            errorTrailingIconColor = onBackgroundColor
        )
    )
}

@Composable
fun ConfirmPasswordTextField(
    password: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    primaryColor: Color,
    onBackgroundColor: Color
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = password,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = "Confirm Password"
            )
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(start = 12.dp),
                imageVector = Icons.Outlined.Lock,
                contentDescription = "confirm password"
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = onBackgroundColor,
            focusedTextColor = onBackgroundColor,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = primaryColor,
            focusedLabelColor = primaryColor,
            cursorColor = primaryColor,
            unfocusedIndicatorColor = onBackgroundColor,
            unfocusedLabelColor = onBackgroundColor,
            unfocusedLeadingIconColor = onBackgroundColor,
            unfocusedPlaceholderColor = onBackgroundColor,
            unfocusedPrefixColor = onBackgroundColor,
            unfocusedSuffixColor = onBackgroundColor,
            unfocusedTrailingIconColor = onBackgroundColor,
            focusedLeadingIconColor = onBackgroundColor,
            focusedTrailingIconColor = onBackgroundColor,
            errorLeadingIconColor = onBackgroundColor,
            errorTrailingIconColor = onBackgroundColor
        )
    )
}