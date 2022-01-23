/**
 * If you are not familiar with React Navigation, refer to the "Fundamentals" guide:
 * https://reactnavigation.org/docs/getting-started
 *
 */
import {FontAwesome, Ionicons} from '@expo/vector-icons';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import {DarkTheme, DefaultTheme, NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import * as React from 'react';
import {Pressable, StyleSheet} from 'react-native';

import Colors from '../constants/Colors';
import useColorScheme from '../hooks/useColorScheme';
import ModalScreen from '../screens/ModalScreen';
import NotFoundScreen from '../screens/NotFoundScreen';
import TabOneScreen from '../screens/TabOneScreen';
import TabProfileScreen from '../screens/TabProfileScreen';
import LinkingConfiguration from './LinkingConfiguration';
import {useTranslation} from "react-i18next";

export default function Navigation({colorScheme}) {
    return (
        <NavigationContainer
            linking={LinkingConfiguration}
            theme={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
            <RootNavigator/>
        </NavigationContainer>
    );
}

/**
 * A root stack navigator is often used for displaying modals on top of all other content.
 * https://reactnavigation.org/docs/modal
 */
const Stack = createNativeStackNavigator();

function RootNavigator() {
    return (
        <Stack.Navigator>
            <Stack.Screen name="Root" component={BottomTabNavigator} options={{headerShown: false}}/>
            <Stack.Screen name="NotFound" component={NotFoundScreen} options={{title: 'Oops!'}}/>
            <Stack.Group screenOptions={{presentation: 'modal'}}>
                <Stack.Screen name="Modal" component={ModalScreen}/>
            </Stack.Group>
        </Stack.Navigator>
    );
}

/**
 * A bottom tab navigator displays tab buttons on the bottom of the display to switch screens.
 * https://reactnavigation.org/docs/bottom-tab-navigator
 */
const BottomTab = createBottomTabNavigator();

function BottomTabNavigator() {
    const colorScheme = useColorScheme();
    const {t} = useTranslation();

    return (
        <BottomTab.Navigator
            initialRouteName="TabOne"
            screenOptions={{
                tabBarActiveTintColor: Colors[colorScheme].tint,
            }}>
            <BottomTab.Screen
                name="TabOne"
                component={TabOneScreen}
                options={{
                    title: 'Tab One',
                    tabBarShowLabel: false,
                    tabBarIcon: ({color, focused}) =>
                        <Ionicons size={24} color={color}
                                  name={focused ? "home" : "home-outline"}/>
                }}
            />
            <BottomTab.Screen
                name="TabProfile"
                component={TabProfileScreen}
                options={({navigation}) => ({
                    title: t('tabProfileTitle'),
                    tabBarShowLabel: false,
                    tabBarIcon: ({color, focused}) =>
                        <FontAwesome size={24} color={color} name={focused ? "user" : "user-o"}/>,
                    headerRight: () => (
                        <Pressable
                            style={({pressed}) => ({
                                opacity: pressed ? 0.5 : 1,
                                marginRight: 15
                            })}
                            onPress={() => navigation.navigate('Modal')}>
                            <FontAwesome name="edit" size={25} color={Colors[colorScheme].text}/>
                        </Pressable>
                    ),
                })}
            />
        </BottomTab.Navigator>
    );
}

const styles = StyleSheet.create({});