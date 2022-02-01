import {FontAwesome, Ionicons} from '@expo/vector-icons';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import {DarkTheme, DefaultTheme, NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import * as React from 'react';
import {StyleSheet} from 'react-native';

import Colors from '../constants/Colors';
import useColorScheme from '../hooks/useColorScheme';
import NotFoundScreen from '../screens/NotFoundScreen';
import TabOneScreen from '../screens/TabOneScreen';
import TabProfileScreen from '../screens/TabProfileScreen';
import LinkingConfiguration from './LinkingConfiguration';
import {useTranslation} from "react-i18next";
import NewItemScreen from "../screens/NewItemScreen";
import EditProfileScreen from "../screens/EditProfileScreen";
import {Pressable} from "../components/Themed";

export default function Navigation({colorScheme}) {
    return (
        <NavigationContainer
            linking={LinkingConfiguration}
            theme={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
            <RootNavigator/>
        </NavigationContainer>
    );
}

const Stack = createNativeStackNavigator();

function RootNavigator() {
    const {t} = useTranslation();
    return (
        <Stack.Navigator>
            <Stack.Screen name="Root" component={BottomTabNavigator} options={{headerShown: false}}/>
            <Stack.Screen name="NotFound" component={NotFoundScreen} options={{title: 'Oops!'}}/>
            <Stack.Group screenOptions={{presentation: 'modal'}}>
                <Stack.Screen
                    name="NewItemModal"
                    component={NewItemScreen}
                    options={{ title: t('tabNewTitle') }}
                />
                <Stack.Screen
                    name="EditProfileModal"
                    component={EditProfileScreen}
                    options={{ title: t('modalEditProfileTitle') }}
                />
            </Stack.Group>
        </Stack.Navigator>
    );
}

const BottomTab = createBottomTabNavigator();

function BottomTabNavigator() {
    // const colorScheme = useColorScheme();
    const colorScheme = 'light';
    const {t} = useTranslation();
    const emptyComponent = () => null

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
                        <Ionicons style={styles.tabDefaultBtn}
                                  color={color} name={focused ? "home" : "home-outline"}/>
                }}
            />
            <BottomTab.Screen
                name="TabNew"
                component={emptyComponent}
                options={{
                    title: t('tabNewTitle'),
                    tabBarShowLabel: false,
                    tabBarIcon: () =>
                        <FontAwesome style={styles.tabNewBtn}
                                     color={Colors[colorScheme].newIcon} name="plus-circle"/>,
                }}
                listeners={({navigation}) => ({
                    tabPress: (e) => {
                        e.preventDefault();
                        navigation.navigate('NewItemModal');
                    }
                })}
            />
            <BottomTab.Screen
                name="TabProfile"
                component={TabProfileScreen}
                options={({navigation}) => ({
                    title: t('tabProfileTitle'),
                    tabBarShowLabel: false,
                    tabBarIcon: ({color, focused}) =>
                        <FontAwesome style={styles.tabDefaultBtn}
                                     color={color} name={focused ? "user" : "user-o"}/>,
                    headerRight: () => (
                        <Pressable
                            style={styles.topRightPress}
                            onPress={() => navigation.navigate('EditProfileModal')}>
                            <FontAwesome
                                name="edit"
                                style={styles.topRightPressIcon}
                                color={Colors[colorScheme].text}/>
                        </Pressable>
                    ),
                })}
            />
        </BottomTab.Navigator>
    );
}

const styles = StyleSheet.create({
    tabNewBtn: {
        marginTop: -20,
        fontSize: 70,
    },
    tabDefaultBtn: {
        fontSize: 24,
    },
    topRightPress: {
        marginRight: 15,
    },
    topRightPressIcon: {
        fontSize: 20
    }
});