package com.osamaaftab.muzz.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osamaaftab.muzz.R
import com.osamaaftab.muzz.presentation.theme.MuzzTypography

@Composable
fun CustomTextFieldApp(
    placeholder: String,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember {
        FocusRequester()
    }
    val colorBorder = MaterialTheme.colorScheme.primary

    Column(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 1.5.dp,
                            shape = RoundedCornerShape(35.dp),
                            color = colorBorder
                        )
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(35.dp)
                        )
                        .focusRequester(focusRequester)
                ) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Box(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MuzzTypography.bodySmall,
                            )
                        }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            innerTextField()
                        }
                    }
                }
            },
        )
    }
}

@Preview
@Composable
fun CustomTextFieldAppPreview() {
    CustomTextFieldApp(
        placeholder = stringResource(id = R.string.type_message),
        onValueChange = { _ -> },
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Send,
        modifier = Modifier
            .padding(end = 16.dp),
    )
}