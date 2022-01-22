import {Text} from './Themed';
import React from "react";

export function MonoText(props) {
    return <Text {...props} style={[props.style, {fontFamily: 'space-mono'}]}/>;
}
