/**
 * Learn more about Light and Dark modes:
 * https://docs.expo.io/guides/color-schemes/
 */

import {
    Image as DefaultImage,
    Pressable as DefaultPressable,
    Text as DefaultText,
    TextInput as DefaultTextInput,
    View as DefaultView
} from 'react-native';

import {Picker as DefaultPicker} from '@react-native-picker/picker';

import Colors from '../constants/Colors';
import React from "react";

export function useThemeColor(props, colorName) {
    // const theme = useColorScheme();
    const theme = 'light';
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
    const {style, lightColor, darkColor, title, ...otherProps} = props;
    const color = useThemeColor({light: lightColor, dark: darkColor}, 'text');

    return (
        <DefaultPressable style={({pressed}) => ({opacity: pressed ? 0.5 : 1})} {...otherProps}>
            <Text style={{color, ...style}}>{title}</Text>
        </DefaultPressable>
    );
}

export function Pressable(props) {
    const {style, lightColor, darkColor, title, children, ...otherProps} = props;
    const color = useThemeColor({light: lightColor, dark: darkColor}, 'text');

    return (
        <DefaultPressable style={({pressed}) => ({opacity: pressed ? 0.5 : 1, color, ...style})} {...otherProps}>
            {children}
        </DefaultPressable>
    );
}

export function TextInput(props) {
    const {style, lightColor, darkColor, ...otherProps} = props;
    const color = useThemeColor({light: lightColor, dark: darkColor}, 'text');
    const backgroundColor = useThemeColor({light: lightColor, dark: darkColor}, 'inputBackground');
    const borderColor = useThemeColor({light: lightColor, dark: darkColor}, 'inputBorder');

    return <DefaultTextInput style={[{color, backgroundColor, borderColor}, style]} {...otherProps} />;
}

export function Picker(props) {
    const {style, lightColor, darkColor, ...otherProps} = props;
    const color = useThemeColor({light: lightColor, dark: darkColor}, 'text');
    const backgroundColor = useThemeColor({light: lightColor, dark: darkColor}, 'inputBackground');
    const borderColor = useThemeColor({light: lightColor, dark: darkColor}, 'inputBorder');

    return <DefaultPicker style={[{color, backgroundColor, borderColor}, style]} {...otherProps} />;
}

