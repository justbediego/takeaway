/**
 * Learn more about Light and Dark modes:
 * https://docs.expo.io/guides/color-schemes/
 */

import {
    Button as DefaultButton,
    Image as DefaultImage,
    StyleSheet,
    Text as DefaultText,
    TextInput as DefaultTextInput,
    View as DefaultView
} from 'react-native';

import Colors from '../constants/Colors';
import useColorScheme from '../hooks/useColorScheme';
import React from "react";

export function useThemeColor(props, colorName) {
    const theme = useColorScheme();
    const colorFromProps = props[theme];

    if (colorFromProps) {
        return colorFromProps;
    } else {
        return Colors[theme][colorName];
    }
}

export function Text(props) {
    const {style, lightColor, darkColor, ...otherProps} = props;
    const color = useThemeColor({light: lightColor, dark: darkColor}, 'text');

    return <DefaultText style={[{color}, style]} {...otherProps} />;
}

export function View(props) {
    const {style, lightColor, darkColor, ...otherProps} = props;
    const backgroundColor = useThemeColor({light: lightColor, dark: darkColor}, 'background');

    return <DefaultView style={[{backgroundColor}, style]} {...otherProps} />;
}

export function Image(props) {
    const {style, lightColor, darkColor, ...otherProps} = props;
    const borderColor = useThemeColor({light: lightColor, dark: darkColor}, 'tintColorLight');

    return <DefaultImage style={[{borderColor}, style]} {...otherProps} />;
}

export function Button(props) {
    const {style, lightColor, darkColor, ...otherProps} = props;
    const color = useThemeColor({light: lightColor, dark: darkColor}, 'text');

    return <DefaultButton style={[{color}, style]} {...otherProps} />;
}

export function TextInput(props) {
    const {style, lightColor, darkColor, ...otherProps} = props;
    const color = useThemeColor({light: lightColor, dark: darkColor}, 'text');
    const backgroundColor = useThemeColor({light: lightColor, dark: darkColor}, 'inputBackground');
    const borderColor = useThemeColor({light: lightColor, dark: darkColor}, 'inputBorder');

    return <DefaultTextInput style={[{color, backgroundColor, borderColor}, style]} {...otherProps} />;
}