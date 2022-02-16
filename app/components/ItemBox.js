import * as React from "react";
import {Text, View} from "./Themed";
import {StyleSheet} from "react-native";

export default function ItemBox({style}) {
    return (
        <View style={{...styles.container, ...style}}>
            <Text>funny text</Text>
        </View>
    );
}


const styles = StyleSheet.create({
    container:{
        borderWidth: 1,
        borderColor: '#D9D9D9',
        height: 200,
        padding:10,
    }
});
