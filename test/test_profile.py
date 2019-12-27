import time
from fixtures import driver
import constants as cnst

def test_profile_data_check(driver):
    button = driver.find_element_by_id(cnst.PROFILE_BUTTON)
    button.click()

    profile_data = driver.find_element_by_xpath(cnst.PROFILE_MENU_PROFILE)
    profile_data.click()

    login = driver.find_element_by_id(cnst.PROFILE_USERNAME)
    email = driver.find_element_by_id(cnst.PROFILE_MAIL)
    time.sleep(1)

    assert login.text == cnst.REGISTER_USERNAME and email.text == cnst.REGISTER_EMAIL
    driver.back()

def test_profile_change_saved(driver):

    # button = driver.find_element_by_id(cnst.PROFILE_BUTTON)
    # button.click()

    profile_data = driver.find_element_by_xpath(cnst.PROFILE_MENU_PROFILE)
    profile_data.click()

    login = driver.find_element_by_id(cnst.PROFILE_USERNAME)
    login.clear()
    login.send_keys('aaa')
    driver.back()
    email = driver.find_element_by_id(cnst.PROFILE_MAIL)
    email.clear()
    email.send_keys('aaa')
    driver.back()

    save_button = driver.find_element_by_id(cnst.PROFILE_UPDATE_BUTTON)

    # driver.back()
    save_button.click()
    driver.back()

    profile_data = driver.find_element_by_xpath(cnst.PROFILE_MENU_PROFILE)
    profile_data.click()

    login = driver.find_element_by_id(cnst.PROFILE_USERNAME)
    email = driver.find_element_by_id(cnst.PROFILE_MAIL)


    assert email.text == 'aaa'
    driver.back()


def test_profile_change_no_saving(driver):

    # button = driver.find_element_by_id(cnst.PROFILE_BUTTON)
    # button.click()

    profile_data = driver.find_element_by_xpath(cnst.PROFILE_MENU_PROFILE)
    profile_data.click()

    login = driver.find_element_by_id(cnst.PROFILE_USERNAME)
    login.send_keys('bbb')
    driver.back()
    email = driver.find_element_by_id(cnst.PROFILE_MAIL)
    email.send_keys('bbb')
    driver.back()
    driver.back()

    profile_data = driver.find_element_by_xpath(cnst.PROFILE_MENU_PROFILE)
    profile_data.click()

    login = driver.find_element_by_id(cnst.PROFILE_USERNAME)
    email = driver.find_element_by_id(cnst.PROFILE_MAIL)

    assert email.text != 'bbb'
