declare module 'react-native-widget-reload' {
  export default class WidgetReload {
    reloadAllWidgets(): void;
    androidConfigure(widgetClassName: string, widgetIdsExtra: string): void;
  }
}
